package com.ddy.weatherease_backend.service;

import com.ddy.weatherease_backend.entity.WeatherData;

import java.util.List;

public interface WeatherService {
    WeatherData getRealTimeWeather(String location);

    List<WeatherData> getWeatherFromDatabase();
    boolean isDataExpired(WeatherData weatherData);
}