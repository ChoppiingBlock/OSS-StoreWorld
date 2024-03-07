package org.spring.service;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.Feign.MasterFeignClient;
import org.spring.controller.FileController;
import org.spring.dto.SystemConstants;
import org.spring.po.FileShardMapping;
import org.spring.tool.FileTransTool;
import org.spring.tool.NacosUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileService {
    @Autowired
    MasterFeignClient masterFeignClient;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    Logger logger = LoggerFactory.getLogger(FileService.class);
    public boolean uploadFile(File file,int parentId ,String path , String userName , String shade_type , int m, int n){

        return true;
    }

    public boolean updateFile(int fileId ,String name){
        return true;
    }

    public boolean downLoadFile(int fileId){
        return true;
    }

    public boolean deleteFile(int fileId){
        return true;
    }

    public String viewDirectory(int parentId,int userId){
        return masterFeignClient.viewDirectory(parentId, userId);
    }

    public String viewFileList(int parentId, int userId) {
        return masterFeignClient.viewFileList(parentId,userId);
    }
    public void addDir(String name ,
                          int parentId ,
                           int userId,
                          String path){
        masterFeignClient.addDirectory(name,parentId,userId,path);

    }

    public boolean addFile(String fileMetadata , String clientIp , String clientPort , String filePath){
        return masterFeignClient.addFile(fileMetadata , clientIp , clientPort , filePath);
    }

    public boolean downloadFile(int fileId){
        String temp = masterFeignClient.getShadeStoreMessage(String.valueOf(fileId));
        logger.info(temp);
        List<FileShardMapping> fileShardMappingList =
                JSON.parseArray(temp,FileShardMapping.class);

        for(FileShardMapping fileShardMapping : fileShardMappingList){
            taskExecutor.execute(()->{
                getFile(fileShardMapping);
            });
        }
        return false;
    }
//todo 文件名从http获得

    public boolean getFile(FileShardMapping fileShardMapping) {
        Map<String , String> params = new HashMap<>();
        params.put("path", fileShardMapping.getShardLocation());
        FileTransTool fileTransTool = new FileTransTool();


        File directory = new File(SystemConstants.clientStorePath+  "\\" + fileShardMapping.getFileId() );
        if (!directory.exists()) { // 检查文件夹是否存在
            if (directory.mkdir()) { // 创建文件夹
                System.out.println("文件夹创建成功！");
            } else {
                System.out.println("文件夹创建失败！");
            }
        }

        try {
            List<String> ipPort = NacosUtil.getIpPort(discoveryClient, SystemConstants.storeServerName, fileShardMapping.getShardStoreServerId());
            fileTransTool.getHttpFileInStream(ipPort.get(0), ipPort.get(1),
                    "/storeServer/sendFile",params,
                    SystemConstants.clientStorePath + "\\" + fileShardMapping.getFileId() + "\\"+ fileShardMapping.getFileId() + "." + fileShardMapping.getShardIndex());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean deleteDir(int dirId){
        return true;
    }

    public boolean updatePermission(int dirId , int fileId , String userName,String type){
        return true;
    }
}
