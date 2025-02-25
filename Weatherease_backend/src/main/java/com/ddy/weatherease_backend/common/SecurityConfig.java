package com.ddy.weatherease_backend.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;

    // 构造器注入JwtUtil
    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 配置 Spring Security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/register", "/users/login","/weather/**").permitAll()  //公开接口
                .antMatchers("/admin/**").hasRole("ADMIN")  //只有ADMIN角色的用户可访问
                .antMatchers("/users/**").hasRole("USER")  //普通用户可访问
                //.antMatchers("/weather/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()  //其他请求都需要认证
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)  // 添加 JWT 认证过滤器
                .formLogin().disable();  // 禁用表单登录
    }

    // 重写 userDetailsService 方法，提供硬编码的用户详情服务
    @Override
    protected UserDetailsService userDetailsService() {
        // 返回一个Lambda表达式的UserDetailsService实现
        return username -> {
            // 根据用户名返回对应的用户详情
            if ("admin".equals(username)) {
                // 如果用户名为"admin"，返回一个硬编码的管理员用户详情
                return User.withUsername("admin")
                        .password("{noop}admin123")  // 使用 {noop} 表示无加密密码，实际中应加密存储密码
                        .roles("ADMIN")
                        .build();
            } else if ("user".equals(username)) {
                // 如果用户名为"user"，返回一个硬编码的普通用户详情
                return User.withUsername("user")
                        .password("{noop}user123")
                        .roles("USER")
                        .build();
            } else {
                // 如果用户名既不是"admin"也不是"user"，抛出用户不存在异常
                throw new UsernameNotFoundException("用户不存在");
            }
        };
    }

    // 定义密码编码器，用于密码的加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 使用 BCrypt 加密密码
    }

    // 重写AuthenticationManager，确保Spring Security可以正确地管理用户认证过程
    /*@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

}
