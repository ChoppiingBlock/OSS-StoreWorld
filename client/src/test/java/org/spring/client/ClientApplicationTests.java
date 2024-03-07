package org.spring.client;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.spring.Feign.MasterFeignClient;
import org.spring.Feign.StoreServer;
import org.spring.po.FileShardMapping;
import org.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;
import java.util.Map;

@SpringBootTest
class  ClientApplicationTests {
    @Autowired
    MasterFeignClient masterFeignClient;

    @Autowired
    StoreServer storeServer;

    @Autowired
    UserService userService;

    @Autowired
    DiscoveryClient discoveryClient;


    @Test
    void contextLoads() {

        String serviceId = "storeServer";
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        for (int i = 0; i < instances.size(); i++) {
            Map<String, String> metadata = instances.get(i).getMetadata();
            System.out.println("Instance Id: " + instances.get(i).getInstanceId());
            System.out.println("Metadata: " + metadata);
            System.out.println(instances.get(i).getInstanceId());
        }

    }

}
