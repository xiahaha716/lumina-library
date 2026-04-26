package com.lumina.common.api;
import lombok.Data;

@Data // 引入 Lombok，自动生成 getter/setter/toString，保持代码极简
public class Result<T> {
    private long code;
    private String message;
    private T data;

    protected Result() {
    }

    protected Result(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 成功时的快捷返回
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 失败时的快捷返回
    public static <T> Result<T> failed(String message) {
        return new Result<>(500, message, null);
    }
}
