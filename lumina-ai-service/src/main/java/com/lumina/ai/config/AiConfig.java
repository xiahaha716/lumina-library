package com.lumina.ai.config;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {

    /**
     * 向 Spring 容器中注入一个“聊天记忆”组件
     * LangChain4j 会自动发现它，并把它装配给你的 Assistant
     */
    @Bean
    public ChatMemory chatMemory() {
        // MessageWindowChatMemory：滑动窗口记忆法
        // withMaxMessages(10)：只记住最近的 10 条对话记录（一问一答算2条）
        // 这样做的好处是：既有了上下文，又不会因为聊天太久导致 Token 撑爆大模型！
        return MessageWindowChatMemory.withMaxMessages(10);
    }
}
