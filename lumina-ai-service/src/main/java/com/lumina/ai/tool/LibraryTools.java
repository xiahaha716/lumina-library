package com.lumina.ai.tool;

import com.lumina.ai.client.BookClient;
import com.lumina.common.api.Result;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component // 必须加这个，让 Spring 管理这个工具类
public class LibraryTools {

    private final BookClient bookClient;

    public LibraryTools(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    /**
     * 这里的 @Tool 注解极其重要！
     * 引号里的文字是写给 AI 看的“说明书”，AI 会根据这句话来判断要不要调用这个方法。
     */
    @Tool("当用户询问图书馆有哪些书、有什么库存、或者查阅书单时，调用此工具获取所有图书信息")
    public String getAllBooksFromLibrary() {
        System.out.println("====== AI 正在偷偷调用图书服务查书 ======");

        // 1. 打电话给图书服务拿数据
        Result<Object> result = bookClient.listBooks();

        // 2. 把拿到的大数据包转成字符串，喂给 AI
        // AI 的理解能力极强，直接把 JSON 格式的字符串扔给它，它就能看懂
        if (result != null && result.getData() != null) {
            return result.getData().toString();
        }
        return "抱歉，目前图书馆没有库存数据。";
    }
}
