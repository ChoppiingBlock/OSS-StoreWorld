package org.spring.master;

import org.junit.jupiter.api.Test;
import org.spring.mapper.UserMapper;
import org.spring.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MasterApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Test
    void contextLoads() {
        System.out.println("--------selectAll method test-------");
        //查询全部用户，参数是一个Wrapper，条件构造器，先不使用为null
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
