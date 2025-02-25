package com.ddy.weatherease_backend.controller;

import com.ddy.weatherease_backend.common.JwtUtil;
import com.ddy.weatherease_backend.dto.UserDTO;
import com.ddy.weatherease_backend.entity.User;
import com.ddy.weatherease_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // 用户注册
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserDTO userDTO) {
        try {
            boolean isRegistered = userService.registerUser(userDTO);

            Map<String, Object> response = new HashMap<>();
            if (isRegistered) {
                response.put("message", "注册成功");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("message", "注册失败");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception e) {
            log.error("用户注册过程中发生异常: {}", e.getMessage(), e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "服务器内部错误");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


        // 用户登录
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) {
        try {
            // 验证用户的用户名和密码
            User user = userService.authenticateUser(userDTO);

            if (user != null) {
                // 登录成功，生成 JWT Token
                String token = jwtUtil.generateToken(user.getUsername());
                log.info("User {} logged in successfully.", user.getUsername());
                return ResponseEntity.ok(token);  //返回200状态码和JWT Token
            } else {
                log.warn("Failed login attempt for username: {}", userDTO.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("无效的凭证");  // 返回401状态码表示未授权
            }
        } catch (Exception e) {
            log.error("登录过程中发生错误: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("服务器内部错误");
        }
    }

}