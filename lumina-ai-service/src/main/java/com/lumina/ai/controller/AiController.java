package com.lumina.ai.controller;
import com.lumina.common.api.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 下面这个 import 是假设AI 客户端叫这个名字。
// 如果之前在 AiController 里用的是其他类名，请保留你原本声明 AI 客户端的代码！
import dev.langchain4j.service.spring.AiService;

@RestController
@RequestMapping("/ai")
public class AiController {

    // 这一段是原本项目中与 LangChain4j 对接的客户端，保留之前的写法即可
    // 假设之前写的是 private final Assistant assistant;
    // public AiController(Assistant assistant) { this.assistant = assistant; }

    @GetMapping(value = "/chat", produces = "application/json;charset=UTF-8")
    public Result<String> chat(@RequestParam String message) {
        try {
            // TODO: 这里是你原本调用 AI 的代码，比如 assistant.chat(message)
            // 把 AI 返回的字符串存入 response 变量
            String response = "这是 AI 给你的回复：" + message; // 此行为占位，请换回你真正的 AI 调用逻辑

            // 用 Result.success() 把字符串包起来
            return Result.success(response);

        } catch (Exception e) {
            // 如果 AI 调用过程中出错，用 Result.error() 把错误信息包起来
            return Result.error("AI 服务调用异常: " + e.getMessage());
        }
    }
}
