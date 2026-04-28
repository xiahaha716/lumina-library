package com.lumina.book.controller;

import com.lumina.book.entity.Book;
import com.lumina.book.mapper.BookMapper;
import com.lumina.common.api.Result;
// --- 新增的 Redisson 和并发相关的包 ---
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import java.util.concurrent.TimeUnit;
// ---------------------------------
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookMapper bookMapper;
    // 1. 新增：声明 Redisson 客户端
    private final RedissonClient redissonClient;

    // 2. 修改：通过构造函数，把 RedissonClient 一起注入进来
    public BookController(BookMapper bookMapper, RedissonClient redissonClient) {
        this.bookMapper = bookMapper;
        this.redissonClient = redissonClient;
    }

    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public Result<List<Book>> listBooks() {
        return Result.success(bookMapper.selectList(null));
    }

    @PostMapping(value = "/add", produces = "application/json;charset=UTF-8")
    public Result<String> addBook(@RequestBody Book book) {
        bookMapper.insert(book);
        return Result.success("图书添加成功！ID 为: " + book.getId());
    }

    @PutMapping(value = "/update", produces = "application/json;charset=UTF-8")
    public Result<String> updateBook(@RequestBody Book book) {
        bookMapper.updateById(book);
        return Result.success("图书修改成功！");
    }

    @DeleteMapping(value = "/delete/{id}", produces = "application/json;charset=UTF-8")
    public Result<String> deleteBook(@PathVariable Long id) {
        bookMapper.deleteById(id);
        return Result.success("ID 为 " + id + " 的图书已安全撤除。");
    }

    // ================== 下面是新增的高并发借书接口 ==================

    @PostMapping(value = "/borrow", produces = "application/json;charset=UTF-8")
    public Result<String> borrowBook(@RequestParam Long bookId, @RequestParam String username) {
        // 定义锁的 Key（粒度要细，锁到具体的每一本书）
        String lockKey = "library:lock:book:" + bookId;
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 尝试加锁 (最多排队等 3 秒，上锁后 10 秒强制自动释放防死锁)
            boolean isLocked = lock.tryLock(3, 10, TimeUnit.SECONDS);

            if (isLocked) {
                System.out.println("🔒 用户 [" + username + "] 拿到锁，开始处理借书业务...");

                // 查库：确认这本书是否存在
                Book book = bookMapper.selectById(bookId);
                if (book == null) {
                    return Result.error("查无此书！");
                }

                /* * 💡 架构师注：
                 * 如果 Book 实体类和数据库表里有“库存(stock)”字段，就在这里加上这段真实业务逻辑：
                 * * if (book.getStock() <= 0) {
                 * return Result.error("手慢了，该书已被借空！");
                 * }
                 * book.setStock(book.getStock() - 1);
                 * bookMapper.updateById(book);
                 */

                // 模拟数据库事务处理耗时
                Thread.sleep(500);

                System.out.println("✅ 借书成功，锁内业务处理完毕！");
                // 为了通用，这里假设 Book 类有个 getName() 方法。如果没有，可以换成 book.getId()
                return Result.success("借书成功！图书 ID [" + book.getId() + "] 已发放入您的借阅清单。");
            } else {
                System.out.println("⚠️ [" + username + "] 没抢到锁，触发限流，排队失败");
                return Result.error("当前借阅该书的人数过多，系统繁忙，请稍后再试。");
            }
        } catch (InterruptedException e) {
            return Result.error("系统发生异常，请联系管理员。");
        } finally {
            // 极其关键：业务执行完，必须释放锁！且只能释放自己加的锁！
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                System.out.println("🔓 业务处理完毕，成功释放分布式锁");
            }
        }
    }
}
