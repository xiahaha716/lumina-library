package com.lumina.book.controller;

import com.lumina.book.entity.Book;
import com.lumina.book.mapper.BookMapper;
import com.lumina.common.api.Result;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookMapper bookMapper;

    public BookController(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
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
}
