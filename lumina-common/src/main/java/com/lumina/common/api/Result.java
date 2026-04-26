package com.lumina.common.api;

/**
 * 全局统一返回结果包装类
 * @param <T> 泛型，表示 data 里面装的具体数据类型
 */
public class Result<T> {

    private Integer code;    // 状态码：200-成功，500-失败
    private String message;  // 提示信息
    private T data;          // 核心返回数据

    // --- 构造方法 ---
    public Result() {}

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // --- 静态快捷包装方法 ---

    /**
     * 成功返回，携带数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    /**
     * 成功返回，不携带数据（比如单纯的删除成功）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "success", null);
    }

    /**
     * 失败返回，携带错误信息（默认 500 状态码）
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(500, message, null);
    }

    /**
     * 失败返回，自定义状态码和错误信息
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    public Integer getCode() { return code; }
    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
