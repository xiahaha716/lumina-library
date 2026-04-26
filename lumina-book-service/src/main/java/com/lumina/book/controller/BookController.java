package com.lumina.book.controller;

import com.lumina.book.entity.Book;
import com.lumina.book.mapper.BookMapper;
// 导入 common 模块下 api 包里的 Result
import com.lumina.common.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookMapper bookMapper;

    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    /**
     * 1. 查询所有图书
     * 返回值从 List<Book> 变成 Result<List<Book>>
     */
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public Result<List<Book>> listBooks() {
        List<Book> books = bookMapper.selectList(null);
        // 【关键点3】用 Result.success() 把查出来的数据包起来
        return Result.success(books);
    }

    /**
     * 2. 根据 ID 查询单本图书
     */
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public Result<Book> getBookById(@PathVariable Long id) {
        Book book = bookMapper.selectById(id);
        return Result.success(book);
    }

    /**
     * 3. 数据库初始化接口
     */
    @GetMapping(value = "/init", produces = "application/json;charset=UTF-8")
    public Result<String> initData() {
        bookMapper.delete(null);

        Book book1 = new Book();
        book1.setTitle("三体");
        book1.setAuthor("刘慈欣");
        book1.setInventory(10);
        book1.setDescription("人类与三体文明的第一次接触。");
        bookMapper.insert(book1);

        Book book2 = new Book();
        book2.setTitle("Java并发编程实战");
        book2.setAuthor("Brian Goetz");
        book2.setInventory(5);
        book2.setDescription("深入理解Java多线程的核心原理。");
        bookMapper.insert(book2);

        return Result.success("数据初始化成功！");
    }
}
