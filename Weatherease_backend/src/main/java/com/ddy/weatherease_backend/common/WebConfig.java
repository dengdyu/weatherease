package com.ddy.weatherease_backend.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    //设置了全局的 CORS 配置，这样就能避免跨域请求被阻止
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许来自 http://localhost:8082 的跨域请求
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")  // 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true); // 允许携带凭证（如cookies）
    }
}
