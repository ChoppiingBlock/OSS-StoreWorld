package org.spring.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.spring.po.DirectoryMetadata;
import org.spring.po.FileMetadata;
import org.spring.po.FileShardMapping;
import org.spring.server.DirectoryService;
import org.spring.server.FileMetaService;
import org.spring.server.FileShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/FileController")
public class FileController {

    @Autowired
    FileShardService fileShardService;

    @Autowired
    FileMetaService fileMetaService;

    @Autowired
    DirectoryService directoryService;




    //接收文件信息
    @GetMapping("/GetUploadFile")
    public boolean getUploadFile(@RequestBody FileMetadata fileMetadata){

        QueryWrapper<FileMetadata> queryWrapper = new QueryWrapper<>();

        //创建分块逻辑
        if(fileMetadata.getType().equals("1")){
            //store数据结构，用以保存状态
                //如何保证store意外掉线：开线槽监听
            // 如何保证clint在store获取途中掉线

            //可运行块，用以发送请求给store

            //交给队列执行

        } else {
            //cal数据结构


            //可运行块，用以发送请求给cal

            //交给队列执行


        }

        return true;
    }

    // 获取文件路径
    @GetMapping("/GetFileBucketPath")
    public void getFileBucketPath(){

    }

    // 查看文件夹
    @GetMapping("/ViewDirectory")
    @ResponseBody
    public String viewDirectory(@RequestParam(name = "parentId" , defaultValue = "0") int parentId,
                                @RequestParam(name = "userId" , defaultValue = "0") int userId ) {
        if(parentId == 0)
            return JSON.toJSONString(directoryService.list(
                new QueryWrapper<DirectoryMetadata>().eq("parent_id",parentId).eq("user_id" , userId)));
        return JSON.toJSONString(directoryService.list(
                new QueryWrapper<DirectoryMetadata>().eq("parent_id",parentId)));
    }


    // 增加文件夹
    @GetMapping("/AddDirectory")
    @ResponseBody
    public boolean addDirectory(@RequestParam("name")String name ,
                             @RequestParam(name = "parentId" , defaultValue = "0")int parentId ,
                             @RequestParam("userId") int userId,
                             @RequestParam("path")String path) {

        directoryService.getBaseMapper().insert(DirectoryMetadata.builder().name(name).parentId(parentId).
                userId(userId).path(path).createDate(DateUtil.date().toLocalDateTime()).build());
        return true;
    }


    // 查看文件列表
    @GetMapping("/ViewFileList")
    @ResponseBody
    public String viewFileList(@RequestParam(name = "parentId" , defaultValue = "0") int parentId,
                                @RequestParam(name = "userId" , defaultValue = "0") int userId ) {

        return JSON.toJSONString(fileMetaService.list(
                new QueryWrapper<FileMetadata>().eq("parent_id",parentId)));
    }

    /**
     * @author flz
     * @apiNote client调用
     * @param  filePath-代表文件在client的地址 , ip ,port 均未client参数
     *
     */
    @GetMapping("/AddFile")
    @ResponseBody
    public boolean addFile(@RequestParam(name = "fileMetadata" , defaultValue = "0")String fileMetadataJson
            ,@RequestParam(name = "clientIp" , defaultValue = "0")String fileIp
            ,@RequestParam(name = "clientPort" , defaultValue = "0")String filePort
            ,@RequestParam(name = "clientPath" , defaultValue = "0")String filePath) {
        FileMetadata fileMetadata = JSON.parseObject(fileMetadataJson,FileMetadata.class);
        fileMetaService.getBaseMapper().insert(fileMetadata);
        fileShardService.sendMetadataToStore(fileMetadata,fileIp,filePort,filePath);
        return true;
    }

    /**
     * 获得分片的对应服务的列表
     *
     * @param fileId
     * @return java.lang.String
     * @date 2024/3/3 16:47
     */
    @GetMapping("/getShadeStoreMessage")
    @ResponseBody
    public String getShadeStoreMessage(@RequestParam("fileId") String fileId){
        List<FileShardMapping> fileShardMappings =  fileShardService.getBaseMapper().selectList(new QueryWrapper<FileShardMapping>().eq("file_id",fileId));
        return JSON.toJSONString(fileShardMappings);
    }

    // 删除文件
    @GetMapping("/DeleteFile")
    public void deleteFile(){

    }

    // 修改文件夹
    @GetMapping("/EditDirectory")
    public void editDirectory(){

    }

    // 修改文件信息
    @GetMapping("/EditFile")
    public void editFile() {
        // 实现修改文件信息的方法
    }

    // 修改文件夹信息
    @GetMapping("/EditDirectoryInfo")
    public void editDirectoryInfo() {
        // 实现修改文件夹信息的方法
    }

    // 查看文件信息
    @GetMapping("/ViewFileInfo")
    public void viewFileInfo() {
        // 实现查看文件信息的方法
    }

    // 查看文件夹信息
    @GetMapping("/ViewDirectoryInfo")
    public void viewDirectoryInfo() {
        // 实现查看文件夹信息的方法
    }

    // 返回文件夹内所有文件信息
    @GetMapping("/AllFilesInDirectory")
    public void getAllFilesInDirectory() {
        // 实现返回文件夹内所有文件信息的方法
    }




}
