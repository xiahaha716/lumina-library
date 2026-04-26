package com.lumina.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
        System.out.println("----------------------------------------");
        System.out.println("📚 Lumina 图书核心服务已启动！端口: 8082");
        System.out.println("----------------------------------------");
    }
}
