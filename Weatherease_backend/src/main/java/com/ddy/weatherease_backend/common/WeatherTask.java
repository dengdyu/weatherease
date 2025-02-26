package com.ddy.weatherease_backend.common;

import com.ddy.weatherease_backend.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@EnableScheduling  // 启用定时任务
@Component
public class WeatherTask {

    private final WeatherService weatherService;
    @Value("${weather.location}")  //默认值为示例地点
    private String location;

    public WeatherTask(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    // 每10分钟执行一次
    @Scheduled(cron = "0 */10 * * * *")
    public void fetchWeatherData() {
        try {
            weatherService.getRealTimeWeather(location);
            log.info("Successfully fetched weather data for location: {}", location);
        } catch (Exception e) {
            log.error("Failed to fetch weather data for location: {}. Error: {}", location, e.getMessage(), e);
        }
    }
}