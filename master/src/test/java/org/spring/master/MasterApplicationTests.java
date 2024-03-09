package org.spring.master;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.spring.dto.TaskStatus;
import org.spring.mapper.UserMapper;
import org.spring.po.FileMetadata;
import org.spring.tool.Impl.CodecMd5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MasterApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Value("${myapp.greeting}")
    private String greeting;
    @Test
    void contextLoads() {

        System.out.println(TaskStatus.ABANDON.getStatusCode());
    }

}
