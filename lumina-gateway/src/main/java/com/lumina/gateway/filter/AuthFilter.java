package com.lumina.gateway.filter;

import com.lumina.gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();

        System.out.println("🛡️ [网关保安] 收到请求路径: " + path);

        // 1. 白名单放行（登录接口、网页静态资源等不需要 Token 就能看）（加上 /login）
        if (path.equals("/") || path.equals("/index.html") ||
                path.endsWith(".js") || path.endsWith(".css") ||
                path.equals("/login")) { // <--- 这里加上放行登录接口
            return chain.filter(exchange);
        }


        // 2. 核心业务接口拦截：提取 Header 中的 Authorization
        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("❌ [网关保安] 拦截：未携带有效 Token！");
            return intercept(response);
        }

        // 3. 截取真实的 Token 字符串（去掉前 7 个字符 "Bearer "）
        String token = authHeader.substring(7);

        try {
            // 4. 调用“造票机”进行内省校验
            JwtUtil.parseToken(token);
            System.out.println("✅ [网关保安] 验票通过，放行请求！");
            // 校验通过，放行
            return chain.filter(exchange);
        } catch (Exception e) {
            // 如果 Token 篡改了、过期了，这里就会捕捉到异常
            System.out.println("❌ [网关保安] 拦截：Token 伪造或已过期！");
            return intercept(response);
        }
    }

    /**
     * 拦截请求，返回 401 未授权状态码
     */
    private Mono<Void> intercept(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete(); // 终止请求，直接返回响应
    }

    /**
     * 实现 Ordered 接口：决定过滤器的执行顺序。
     * 数字越小，优先级越高。我们给 -1 保证它在最前面执行。
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
