package com.lumina.ai.client;

import com.lumina.common.api.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "book-service", url = "http://localhost:8082")
public interface BookClient {

    // 1. 【查】
    @GetMapping("/book/list")
    Result<Object> listBooks();

    // 2. 【增】AI 服务没有 Book 实体类，所以我们用极其灵活的 Map 来包装 JSON 发送过去
    @PostMapping(value = "/book/add", consumes = "application/json")
    Result<String> addBook(@RequestBody Map<String, Object> book);

    // 3. 【删】
    @DeleteMapping("/book/delete/{id}")
    Result<String> deleteBook(@PathVariable("id") Long id);
}
