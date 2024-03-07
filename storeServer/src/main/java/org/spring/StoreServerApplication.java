package org.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("org.spring.mapper")

public class StoreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreServerApplication.class, args);
    }

}
