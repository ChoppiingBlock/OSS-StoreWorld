package org.spring.service;


import org.spring.Feign.MasterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    MasterFeignClient masterFeignClient;

    public boolean login(String userName, String password) {
        return masterFeignClient.login(userName, password);
    }
}