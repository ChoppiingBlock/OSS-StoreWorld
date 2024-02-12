package org.spring.calculateserver;

import org.junit.jupiter.api.Test;
import org.spring.service.FileService;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class CalculateServerApplicationTests {

    @Test
    void contextLoads() throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();
        FileService fileService = new FileService();
        FileService.downloadFile();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; // 运行时间（毫秒）

        System.out.println("运行时间：" + duration + "毫秒");
    }

}
