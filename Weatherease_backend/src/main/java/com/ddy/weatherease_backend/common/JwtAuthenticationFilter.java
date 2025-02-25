package com.ddy.weatherease_backend.common;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * 执行过滤逻辑，本方法从HTTP请求中提取JWT Token，验证其有效性，并设置相应的认证信息到SecurityContext中
     * 如果Token无效或已过期，请求将不做任何处理直接传递给下一个过滤器或servlet
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取Token
        String token = request.getHeader("Authorization");

        // 检查Token是否非空且以"Bearer "开头
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // 提取 JWT Token

            // 检查Token是否已过期
            if (jwtUtil.isTokenExpired(token)) {
                // 如果 Token 过期，可以在这里处理，例如抛出异常或者让用户重新登录
                filterChain.doFilter(request, response);
                return;
            }

            // 解析 Token 获取用户名
            String username = jwtUtil.parseToken(token);

            // 如果用户名非空，创建认证对象并设置到SecurityContext中
            if (username != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 无论如何，继续执行过滤器链中的下一个过滤器或servlet
        filterChain.doFilter(request, response);
    }
}