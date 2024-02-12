package org.spring.Feign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.cloud.openfeign.FeignClient(name = "master" , url = "http://127.0.0.1:8071")
public interface MasterFeignClient {
    @PostMapping("/UserController/Login")
    boolean login(@RequestParam("userName") String userName, @RequestParam("password") String password);
}
