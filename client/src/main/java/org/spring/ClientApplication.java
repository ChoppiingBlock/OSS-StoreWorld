package org.spring;

import org.spring.config.ApplicationContextProvider;
import org.spring.flowcontrol.AllStream;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@EnableFeignClients
@SpringBootApplication()

public class ClientApplication {
    @Autowired
    UserService userService;

    @Autowired
    AllStream allStream;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        System.out.println("hello world hello world ");

        // 在新线程中运行 allStream.allStream()
        ApplicationContext context = ApplicationContextProvider.getContext();
        AllStream allStreamInstance = context.getBean(AllStream.class);

        Thread thread = new Thread(() -> {
            try {
                allStreamInstance.allStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }



}
