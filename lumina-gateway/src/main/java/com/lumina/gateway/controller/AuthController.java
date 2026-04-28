package com.lumina.gateway.controller;

import com.lumina.gateway.util.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

     //假登录接口：只要调用就发 Token

    @GetMapping("/login")
    public Map<String, Object> login(@RequestParam(defaultValue = "Guest") String username) {

        // 1. 动态生成 Token (有效期 24 小时)
        String token = JwtUtil.generateToken(username, "user");

        System.out.println("🎟️ [授权中心] 成功为用户 " + username + " 签发了 Token!");

        // 2. 组装返回给前端的 JSON 数据
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "登录成功");
        // 为了前端方便，我们直接把 "Bearer " 前缀拼好
        result.put("token", "Bearer " + token);

        return result;
    }
}
