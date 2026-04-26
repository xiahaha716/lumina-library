package com.lumina.ai.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {

    // 注入 LangChain4j 提供的大模型客户端
    private final ChatLanguageModel chatLanguageModel;

    public AiController(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    /**
     * 极简对话测试接口
     */
    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        // 调用大模型生成回答
        return chatLanguageModel.generate(message);
    }
}
