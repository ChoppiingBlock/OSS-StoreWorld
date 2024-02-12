package org.spring.client;

import org.junit.jupiter.api.Test;
import org.spring.Feign.MasterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientApplicationTests {
    @Autowired
    MasterFeignClient masterFeignClient;

    @Test
    void contextLoads() {
        System.out.println( masterFeignClient.login("user1", "password1"));
    }

}
