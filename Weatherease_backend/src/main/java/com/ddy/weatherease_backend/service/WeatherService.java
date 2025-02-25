package com.ddy.weatherease_backend.service;

import com.ddy.weatherease_backend.entity.WeatherData;

public interface WeatherService {
    WeatherData getRealTimeWeather(String location);

    WeatherData getWeatherFromDatabase();
    boolean isDataExpired(WeatherData weatherData);
}
