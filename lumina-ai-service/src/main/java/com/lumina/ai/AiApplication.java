package com.lumina.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);

        // 加上你要求的启动成功提示
        System.out.println("\n----------------------------------------------------------");
        System.out.println("🤖 Lumina AI 核心服务已成功启动！端口: 8081");
        System.out.println("----------------------------------------------------------\n");
    }
}
