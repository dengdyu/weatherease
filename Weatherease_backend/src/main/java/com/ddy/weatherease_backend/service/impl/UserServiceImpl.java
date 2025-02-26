package com.ddy.weatherease_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ddy.weatherease_backend.dto.UserDTO;
import com.ddy.weatherease_backend.entity.User;
import com.ddy.weatherease_backend.mapper.UserMapper;
import com.ddy.weatherease_backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean isUsernameTaken(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);  // 根据用户名查询
        return userMapper.selectCount(queryWrapper) > 0;  // 如果查询结果大于0，说明用户名已存在
    }

    @Override
    public boolean registerUser(UserDTO userDTO) {
        if (isUsernameTaken(userDTO.getUsername())) {
            return false;  //用户名已存在，注册失败
        }
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));  // 密码加密
        user.setEmail(userDTO.getEmail());
        user.setRole("USER");  // 默认角色为普通用户
        userMapper.insert(user);  // 使用 MyBatis-Plus 插入数据
        return true;
    }

    @Override
    // 用户登录验证
    public User authenticateUser(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        User user = userMapper.selectOne(queryWrapper);  // 使用 QueryWrapper 查找用户
        //比较明文密码和加密后的密码是否匹配
        if (user != null && passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            log.info("User authenticated successfully: {}", userDTO.getUsername());
            return user;  // 密码匹配，返回用户信息
        } else {
            log.warn("Password mismatch for user: {}", userDTO.getUsername());
            return null;  // 登录失败
        }
    }

}
