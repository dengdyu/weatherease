package com.ddy.weatherease_backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("weather_data")
public class WeatherData {

    @TableId
    private Long id;

    private String location;

    private BigDecimal temperature;

    @TableField("feels_like")
    private BigDecimal feelsLike;

    @TableField("weather_description")
    private String weatherDescription;

    private BigDecimal wind360;

    @TableField("wind_dir")
    private String windDir;

    @TableField("wind_scale")
    private BigDecimal windScale;

    @TableField("wind_speed")
    private BigDecimal windSpeed;

    private BigDecimal humidity;

    private BigDecimal precip;

    private BigDecimal pressure;

    private BigDecimal visibility;

    private BigDecimal cloud;

    @TableField("dew_point")
    private BigDecimal dewPoint;

    @TableField("obs_time")
    private LocalDateTime obsTime;
}
