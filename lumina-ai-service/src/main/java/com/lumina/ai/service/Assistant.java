package com.lumina.ai.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

/**
 * 这是 LangChain4j 的核心代理接口
 * @AiService 注解会让 Spring 自动为你生成一个具备 AI 能力的实现类
 */
@AiService
public interface Assistant {

    /**
     * @SystemMessage 就是 AI 的“人设”或“系统提示词”(Prompt)
     * 你可以在这里规定它的语气、职责和行为准则
     */
    @SystemMessage("你是一个极具亲和力的图书馆智能助手 Lumnia。请用温柔、专业的语气回答用户的问题。当你需要查询图书库存、列表或详情时，必须主动使用你拥有的工具去查阅，根据查到的真实数据再回答用户。")
    String chat(String userMessage);
}
