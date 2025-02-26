package com.ddy.weatherease_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddy.weatherease_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
