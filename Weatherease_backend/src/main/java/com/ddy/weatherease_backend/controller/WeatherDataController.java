package com.ddy.weatherease_backend.controller;

import com.ddy.weatherease_backend.entity.WeatherData;
import com.ddy.weatherease_backend.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherDataController {

    @Autowired
    private WeatherService weatherService;

    @Value("${weather.location}")
    private String defaultLocation;

    // 获取实时天气数据
    @GetMapping("/now/{location}")
    public ResponseEntity<?> getWeather(@PathVariable String location) {
        WeatherData weatherData = weatherService.getRealTimeWeather(location);
        if (weatherData == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No weather data available for location: " + location);
        }
        return ResponseEntity.ok(weatherData);
    }

    //获取1条实时信息
    /*@GetMapping("/now")
    public ResponseEntity<?> getDefaultWeather() {
        WeatherData weatherData = weatherService.getWeatherFromDatabase();

        // 如果数据库没有数据或数据过期，调用API获取并更新数据库
        if (weatherData == null || weatherService.isDataExpired(weatherData)) {
            weatherData = weatherService.getRealTimeWeather(defaultLocation);
        }

        if (weatherData != null) {
            return ResponseEntity.ok(weatherData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No weather data available.");
        }
    }*/

    //获取5条实时信息
    @GetMapping("/now")
    public ResponseEntity<?> getDefaultWeather() {
        List<WeatherData> weatherData = weatherService.getWeatherFromDatabase();

        // 如果数据库没有数据或数据过期，调用API获取并更新数据库
        if (weatherData.isEmpty() || weatherService.isDataExpired(weatherData.get(0))) {
            WeatherData latestWeatherData = weatherService.getRealTimeWeather(defaultLocation);
            weatherData.add(latestWeatherData);
            log.info("数据过期，重新获取实时数据" + latestWeatherData);
        }
        if (!weatherData.isEmpty()) {
            return ResponseEntity.ok(weatherData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No weather data available.");
        }
    }



}