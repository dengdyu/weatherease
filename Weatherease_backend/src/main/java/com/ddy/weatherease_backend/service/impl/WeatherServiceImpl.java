package com.ddy.weatherease_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ddy.weatherease_backend.entity.WeatherData;
import com.ddy.weatherease_backend.mapper.WeatherDataMapper;
import com.ddy.weatherease_backend.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    @Value("${weather.api.url}")  // 从配置文件读取API的URL
    private String apiUrl;

    @Value("${weather.api.token}")  // 从配置文件读取API的Token
    private String apiToken;

    @Value("${weather.location}")
    private String location;

    @Autowired
    private WeatherDataMapper weatherDataMapper;

    @Autowired
    private RestTemplate restTemplate;

    // 原来的获取天气数据方法
    public String getJson(String url) throws Exception {
        // 发送 HTTP 请求
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Encoding", "gzip"); // 支持 gzip 编码

        // 获取响应流
        InputStream inputStream = connection.getInputStream();

        // 如果响应是 GZIP 编码，解压缩
        if ("gzip".equals(connection.getContentEncoding())) {
            inputStream = new GZIPInputStream(inputStream);  // 解压 GZIP 数据
        }

        // 读取解压后的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();
        return response.toString();  // 返回解压后的 JSON 响应
    }

    public WeatherData getRealTimeWeather(String location) {
        try {
            String url = String.format("%s?location=%s&key=%s", apiUrl, location, apiToken);
            String response = getJson(url);

            // 使用 fastjson 解析 JSON 数据
            JSONObject weatherDataJson = JSON.parseObject(response);

            WeatherData weatherData = new WeatherData();
            weatherData.setLocation(location);
            weatherData.setTemperature(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("temp"))));
            weatherData.setFeelsLike(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("feelsLike"))));
            weatherData.setWeatherDescription(weatherDataJson.getJSONObject("now").getString("text"));
            weatherData.setWind360(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("wind360"))));
            weatherData.setWindDir(weatherDataJson.getJSONObject("now").getString("windDir"));
            weatherData.setWindScale(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("windScale"))));
            weatherData.setWindSpeed(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("windSpeed"))));
            weatherData.setHumidity(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("humidity"))));
            weatherData.setPrecip(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("precip"))));
            weatherData.setPressure(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("pressure"))));
            weatherData.setVisibility(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("vis"))));
            weatherData.setCloud(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("cloud"))));
            weatherData.setDewPoint(BigDecimal.valueOf(Double.parseDouble(weatherDataJson.getJSONObject("now").getString("dew"))));
            weatherData.setObsTime(ZonedDateTime.parse((weatherDataJson.getJSONObject("now").getString("obsTime"))).toLocalDateTime());

            WeatherData existingWeatherData = weatherDataMapper.findByLocationAndObsTime(location, weatherData.getObsTime());

            // 如果存在相同记录，跳过插入
            if (existingWeatherData != null) {
                System.out.println("Duplicate data found, skipping insert.");
                return existingWeatherData;  // 返回已存在的记录
            }

            // 将数据保存到数据库
            weatherDataMapper.insert(weatherData);

            return weatherData;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取数据库中最新的天气数据1条
    /*@Override
    public WeatherData getWeatherFromDatabase() {
        WeatherData latestWeather = weatherDataMapper.findLatestWeather();
        if (latestWeather != null) {
            log.info("Latest weather data from database:{} " , latestWeather);
        } else {
            log.error("No weather data found in the database.");
        }
        return latestWeather;
    }*/

    //获取数据库中最新的天气数据5条
    @Override
    public List<WeatherData> getWeatherFromDatabase() {
        List<WeatherData> latest5Weather = weatherDataMapper.find5LatestWeather();
        List<WeatherData> filteredData = filterByTimeGap(latest5Weather);
        if (!filteredData.isEmpty()) {
            log.info("Latest weather data from database:{} " , filteredData);
        } else {
            log.error("No weather data found in the database.");
        }
        return filteredData;
    }

    private List<WeatherData> filterByTimeGap(List<WeatherData> latest5Weather) {
        // 过滤掉时间差超过1小时的记录
        List<WeatherData> finalData = new ArrayList<>();
        LocalDateTime previousTime = null;

        for (WeatherData data : latest5Weather) {
            LocalDateTime currentTime = data.getObsTime();
            if (previousTime == null || Duration.between(previousTime, currentTime).toHours() >= 0) {
                finalData.add(data);
                previousTime = currentTime;
            }
        }
        log.info("Filtered weather data:{} " , finalData);
        return finalData;
    }

    @Override
    public boolean isDataExpired(WeatherData weatherData) {
        return Duration.between(weatherData.getObsTime(), LocalDateTime.now()).toHours() > 1;
    }

}