package com.lumina.ai.tool;

import com.lumina.ai.client.BookClient;
import com.lumina.common.api.Result;
import dev.langchain4j.agent.tool.P; // 引入参数描述注解
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LibraryTools {

    private final BookClient bookClient;

    public LibraryTools(BookClient bookClient) {
        this.bookClient = bookClient;
    }

    @Tool("当用户询问图书馆有哪些书、有什么库存、或者查阅书单时，调用此工具获取所有图书信息")
    public String getAllBooksFromLibrary() {
        System.out.println("====== AI 正在调用 [查询图书] 工具 ======");
        Result<Object> result = bookClient.listBooks();
        return result != null && result.getData() != null ? result.getData().toString() : "无数据";
    }

    // ================== 下面是新增的“机械臂”工具 ==================

    @Tool("当用户要求添加、购入或新进一本书籍时调用此工具。")
    public String addBook(
            @P("书的名称") String title,
            @P("书的作者，如果用户没提供可填'佚名'") String author,
            @P("新增的库存数量，默认填 1") int inventory) {

        System.out.println("====== AI 正在挥动机械臂：[添加图书] ======");

        // 1. 把 AI 提取出的参数打包成 Map
        Map<String, Object> newBook = new HashMap<>();
        newBook.put("title", title);
        newBook.put("author", author);
        newBook.put("inventory", inventory);
        newBook.put("description", "由 AI 智能助理自动录入"); // 悄悄打个标记

        // 2. 调用远程接口存入数据库
        Result<String> result = bookClient.addBook(newBook);

        // 3. 把操作结果告诉 AI 的大脑
        return "数据库执行结果：" + result.getData();
    }

    @Tool("当用户明确要求删除、下架某本特定 ID 的书时调用此工具。必须先查明该书的 ID。")
    public String deleteBook(@P("要删除的书籍的数字 ID") Long id) {
        System.out.println("====== AI 正在挥动机械臂：[删除图书] ID: " + id + " ======");
        Result<String> result = bookClient.deleteBook(id);
        return "数据库执行结果：" + result.getMessage();
    }
}
