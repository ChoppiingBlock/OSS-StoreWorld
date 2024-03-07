package org.spring.dto;

import org.springframework.stereotype.Component;

/**
 * @author flz
 * @apiNote 系统常量
 */

@Component
public class SystemConstants {

    //分块方案均分 ， 1
    public static final String shadeTypeEqual = "1";
    //分块方案EC算法 ， 2
    public static final String shadeTypeEC = "2";

    //均分分块大小
    public static final int shadeSize = 1024 * 64;


    //分片存储地址
    public static final String testStorePath = "C:\\05program\\bucketWorld\\files\\output";

    //client1文件保存地址
    public static final String clientStorePath = "C:\\05program\\bucketWorld\\files\\client1";

    //metadata的服务标识的key
    public static final String serverId = "serverId";

    //storeServer的服务名
    public static final String storeServerName = "storeServer";


}
