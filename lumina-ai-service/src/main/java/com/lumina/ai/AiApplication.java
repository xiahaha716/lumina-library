package com.lumina.ai;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AiApplication {
    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
        System.out.println("----------------------------------------");
        System.out.println("🧠 Lumina AI 超级大脑服务已成功启动！端口: 8081");
        System.out.println("----------------------------------------");
    }
}
