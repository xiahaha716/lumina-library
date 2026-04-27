package com.lumina.ai.client;

import com.lumina.common.api.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// name 随便起个名字标识服务，url 指向我们图书服务的真实地址
@FeignClient(name = "book-service", url = "http://localhost:8082")
public interface BookClient {

    @GetMapping("/book/list")
    Result<Object> listBooks();
}
