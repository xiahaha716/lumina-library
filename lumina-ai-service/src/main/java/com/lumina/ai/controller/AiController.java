package com.lumina.ai.controller;

import com.lumina.common.api.Result;
import com.lumina.ai.client.BookClient;
import com.lumina.ai.service.Assistant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final BookClient bookClient;
    private final Assistant assistant; // 【新增】注入你真正的 AI 大脑

    // 构造器注入
    public AiController(BookClient bookClient, Assistant assistant) {
        this.bookClient = bookClient;
        this.assistant = assistant;
    }

    /**
     * 真正的 AI 聊天接口
     */
    @GetMapping(value = "/chat", produces = "application/json;charset=UTF-8")
    public Result<String> chat(@RequestParam String message) {
        try {
            // 【核心修改】删掉那句写死的字符串，让 AI 真正去思考并调用工具！
            String response = assistant.chat(message);

            return Result.success(response);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("AI 服务调用异常: " + e.getMessage());
        }
    }

    // 之前的测试接口，保留着也没关系
    @GetMapping(value = "/test-feign", produces = "application/json;charset=UTF-8")
    public Result<Object> testFeignCall() {
        return bookClient.listBooks();
    }
}
