package com.lumina.book.controller;

import com.lumina.book.entity.Book;
import com.lumina.book.mapper.BookMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 图书业务接口
 * 访问路径前缀: /book
 */
@RestController
@RequestMapping("/book")
public class BookController {

    private final BookMapper bookMapper;

    // 使用构造器注入 Mapper
    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    /**
     * 1. 查询所有图书
     * 访问地址: http://localhost:8888/book/list
     * produces 确保即使浏览器识别有问题，也能强制以 UTF-8 展示中文 JSON
     */
    @GetMapping(value = "/list", produces = "application/json;charset=UTF-8")
    public List<Book> listBooks() {
        // selectList(null) 表示没有任何条件，查询全表数据
        return bookMapper.selectList(null);
    }

    /**
     * 2. 根据 ID 查询单本图书 (演示路径变量)
     * 访问示例: http://localhost:8888/book/1
     */
    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public Book getBookById(@PathVariable Long id) {
        return bookMapper.selectById(id);
    }

    /**
     * 3. 数据库初始化接口 (终极乱码解决方案)
     * 访问地址: http://localhost:8888/book/init
     */
    @GetMapping(value = "/init", produces = "text/plain;charset=UTF-8")
    public String initData() {
        // 先清空表，防止重复插入
        bookMapper.delete(null);

        // 插入《三体》
        Book book1 = new Book();
        book1.setTitle("三体");
        book1.setAuthor("刘慈欣");
        book1.setInventory(10);
        book1.setDescription("人类与三体文明的第一次接触。");
        bookMapper.insert(book1);

        // 插入《Java并发编程实战》
        Book book2 = new Book();
        book2.setTitle("Java并发编程实战");
        book2.setAuthor("Brian Goetz");
        book2.setInventory(5);
        book2.setDescription("深入理解Java多线程的核心原理。");
        bookMapper.insert(book2);

        return "数据初始化成功！请访问 /book/list 查看中文是否正常。";
    }
}
