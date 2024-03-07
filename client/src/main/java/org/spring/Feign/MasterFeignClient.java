package org.spring.Feign;

import com.alibaba.fastjson.JSON;
import org.spring.po.FileShardMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@FeignClient(name = "master" , url = "http://127.0.0.1:8071")
public interface MasterFeignClient {
    @PostMapping("/UserController/Login")
    int login(@RequestParam("userName") String userName, @RequestParam("password") String password);

    @PostMapping("/UserController/Register")
    int register(@RequestParam("userName") String userName, @RequestParam("password") String password);

    @GetMapping("/FileController/ViewDirectory")
    String viewDirectory(@RequestParam(name = "parentId" , defaultValue = "0") int parentId,@RequestParam(name = ("userId") ,defaultValue = "0") int userId);
    @GetMapping("/FileController/AddDirectory")
    void addDirectory(@RequestParam("name")String name ,
                             @RequestParam("parentId")int parentId ,
                             @RequestParam("userId") int userId,
                             @RequestParam("path")String path) ;
    @GetMapping("/FileController/ViewFileList")
    String viewFileList(@RequestParam(name = "parentId" , defaultValue = "0") int parentId,
                               @RequestParam(name = "userId" , defaultValue = "0") int userId );
    @GetMapping("/FileController/AddFile")
    boolean addFile(@RequestParam(name = "fileMetadata" , defaultValue = "0")String fileMetadataJson
            ,@RequestParam(name = "clientIp" , defaultValue = "0")String fileIp
            ,@RequestParam(name = "clientPort" , defaultValue = "0")String filePort
            ,@RequestParam(name = "clientPath" , defaultValue = "0")String filePath);

    @GetMapping("/FileController/getShadeStoreMessage")
    String getShadeStoreMessage(@RequestParam("fileId") String fileId);
}
