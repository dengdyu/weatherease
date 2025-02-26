package com.ddy.weatherease_backend.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT 工具类
 */
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secret_key";  // 密钥
    private static final long EXPIRATION_TIME = 864_000_000L; // 10天过期时间

    /**
     * 生成 JWT Token
     *
     * @param username 用户名，用于标识令牌的主体
     * @return 生成的 JWT Token 字符串
     */
    public String generateToken(String username) {
        // 使用 Jwts.builder() 构建 JWT 实例
        return Jwts.builder()
                // 设置令牌的主体部分，这里使用用户名作为标识
                .setSubject(username)
                // 设置令牌的签发时间，为当前日期和时间
                .setIssuedAt(new Date())
                // 设置令牌的过期时间，从当前时间开始加上指定的过期时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                // 使用 HS512 算法和密钥签名令牌，以确保令牌的安全性和完整性
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // 将令牌压缩为字符串形式，便于传输和存储
                .compact();
    }

    /**
     * 解析 JWT Token，返回用户名
     *
     * 该方法主要负责验证和解析JWT Token，提取出用户名（即JWT中的Subject）
     * 它使用了JWT库进行Token的验证和解析，确保Token未被篡改且有效
     *
     * @param token 需要解析的JWT Token字符串
     * @return 解析成功则返回Token中的用户名（Subject）
     *         如果解析失败，将抛出异常
     */
    public String parseToken(String token) {
        // 使用JWT库解析Token，验证签名的正确性，并获取Token的负载部分
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        // 从负载中获取用户名（Subject）
        return claims.getSubject();
    }

    /**
     * 验证 Token 是否过期
     *
     * @param token 待验证的 Token 字符串
     * @return 如果 Token 已过期返回 true，否则返回 false
     */
    public boolean isTokenExpired(String token) {
        // 解析 Token 并获取过期时间
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        // 判断过期时间是否在当前时间之前
        return expiration.before(new Date());
    }
}
