package org.spring.master;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
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


        CodecMd5Util codecMd5Util = new CodecMd5Util();
        System.out.println(codecMd5Util.getFileMd5("C:\\05program\\bucketWorld\\files\\input\\1.mp4"));
    }

}
