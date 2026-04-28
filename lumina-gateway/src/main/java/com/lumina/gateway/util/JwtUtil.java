package com.lumina.gateway.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    // 随便生成一个极其安全的密钥（在企业里通常配在 yml 文件中）
    // 注意：JWT 要求密钥长度必须足够长，这里用 Keys.secretKeyFor 自动生成一个符合要求的安全密钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Token 过期时间，这里设置为 24 小时
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    /**
     * 生成 JWT Token (签发入场券)
     * @param username 用户名
     * @param role 用户角色 (比如 "admin", "user")
     */
    public static String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // 主题（通常放用户名或用户ID）
                .claim("role", role)  // 自定义载荷（这里放了角色）
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 过期时间
                .signWith(SECRET_KEY) // 签名
                .compact();
    }

    /**
     * 解析并校验 JWT Token (查验入场券)
     * 如果 Token 被篡改或已过期，这里会抛出异常
     */
    public static Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

