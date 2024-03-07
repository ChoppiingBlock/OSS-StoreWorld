package org.spring.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.spring.dto.SystemConstants;
import org.spring.tool.FileTransTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Controller
@RequestMapping("/FileController")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    /**
     * 被动，根据参数，返回文件流
     *
     * @param path 文件路径
     * @param index 文件分片
     * @param offset 文件偏移
     * @param response 响应体
     * @date 2024/3/3 15:42
     */

    @GetMapping("/sendFile")
    public void sendFile(@RequestParam("path") String path ,
                                           @RequestParam("index") int index ,
                                           @RequestParam(value = "offset", defaultValue = "0") int offset,
                         HttpServletResponse response) throws IOException {
        FileTransTool fileTransTool = new FileTransTool();
        fileTransTool.sendFileStreamByPath(path, index, offset, response);
//        Runnable sender = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    FileTransTool fileTransTool = new FileTransTool();
//                    fileTransTool.sendFileStreamByPath(path, index, offset, response);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        };
//        taskExecutor.execute(sender);
    }


    /**
     * 调用master 的 AddFile，来主动上传文件
     *
     * @date 2024/3/3 15:46
     */

    @GetMapping("/uploadFIle")
    public void uploadFile(@RequestParam(name = "fileMetadata" , defaultValue = "0")String fileMetadataJson
            ,@RequestParam(name = "fileIp" , defaultValue = "0")String fileIp
            ,@RequestParam(name = "filePort" , defaultValue = "0")String filePort
            ,@RequestParam(name = "filePath" , defaultValue = "0")String filePath) {

    }


    private int FileMaxChunk(File file){
        return (int)Math.ceil(file.length() * 1.0 / SystemConstants.shadeSize );
    }
}
