package org.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Scanner;
@EnableFeignClients
@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        while (isRunning) {
            System.out.print("请输入内容（输入'exit'退出）：");
            String input = scanner.nextLine();

            if ("exit".equals(input)) {
                isRunning = false;
            } else {
                // 处理输入并返回结果
                System.out.println("您输入的内容是：" + input);
            }
        }

        scanner.close();
    }

}
