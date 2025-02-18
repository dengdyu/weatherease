package com.ddy.weatherease_backend.service;

import com.ddy.weatherease_backend.dto.UserDTO;
import com.ddy.weatherease_backend.entity.User;

public interface UserService {
    boolean isUsernameTaken(String username);  // 检查用户名是否已存在

    boolean registerUser(UserDTO userDTO);  // 注册方法

    User authenticateUser(UserDTO userDTO);
}
