package com.ddy.weatherease_backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  //定时
@MapperScan("com.ddy.weatherease_backend.mapper")
public class WeathereaseBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeathereaseBackendApplication.class, args);
    }

}
