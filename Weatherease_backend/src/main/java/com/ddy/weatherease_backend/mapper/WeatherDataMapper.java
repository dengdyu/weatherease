package com.ddy.weatherease_backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ddy.weatherease_backend.entity.WeatherData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WeatherDataMapper extends BaseMapper<WeatherData> {
    // 根据 location 和 obsTime 查找是否存在数据
    @Select("SELECT * FROM weather_data WHERE location = #{location} AND obs_time = #{obsTime} LIMIT 1")
    WeatherData findByLocationAndObsTime(@Param("location") String location, @Param("obsTime") LocalDateTime obsTime);

    @Select("SELECT * FROM weather_data ORDER BY obs_time DESC LIMIT 1")
    WeatherData findLatestWeather();

    @Select("SELECT * FROM weather_data ORDER BY obs_time DESC LIMIT 5")
    List<WeatherData> find5LatestWeather();
}