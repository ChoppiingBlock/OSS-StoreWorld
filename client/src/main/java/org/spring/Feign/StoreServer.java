package org.spring.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name = "storeServer" , url = "http://127.0.0.1:8071")
public interface StoreServer {
    @GetMapping("storeServer/HelloWorld/{instanceId}")
    @ResponseBody
    public String helloWorld(@PathVariable("instanceId") String instanceId,@RequestParam("params") String params);
}
