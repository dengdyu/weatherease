package com.ddy.weatherease_backend.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //全局的CORS配置
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许来自 http://localhost:8081 的跨域请求
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8081","http://localhost:8082")  // 前端地址
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许的请求方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true); // 允许携带凭证（如cookies）
    }
}
