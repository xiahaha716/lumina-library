package com.lumina.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        // 这是微服务启动的心跳！
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("----------------------------------------");
        System.out.println("🚀 Lumina 图书馆网关服务已成功启动！端口: 8888");
        System.out.println("----------------------------------------");
    }
}
