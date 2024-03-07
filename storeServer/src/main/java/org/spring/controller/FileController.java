package org.spring.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.dto.ResultVo;
import org.spring.dto.SystemConstants;
import org.spring.po.FileShardMapping;
import org.spring.service.FileShardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.List;

@RestController
@RequestMapping("/storeServer")
public class FileController {
    @Autowired
    FileShardService fileShardService;

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("HelloWorld")
    @ResponseBody
    public String helloWorld(@RequestParam("params") String params){
        String serviceId = "storeServer";
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        return "hello here is " +instances.get(0).getInstanceId()  + "params";
    }
    @GetMapping("/hello")
    public void hello(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write("Hello, World!");
        writer.flush();
    }

    /**
     * 获得文件分片
     * @param fileIp client ip
     * @param filePort client port
     * @param filePath client文件地址
     * @return org.spring.dto.MasterResultVo<java.lang.Object>
     * @date 2024/2/17 22:06
     */

    @GetMapping("/getShade")
    @ResponseBody
    public ResultVo<Object> getShade(@RequestParam("fileShardMapping") String fileShardMapping
            , @RequestParam("fileIp") String fileIp
            , @RequestParam("filePort") String filePort
            , @RequestParam("offset") int offset
            , @RequestParam("filePath") String filePath){
        logger.info("get res and fsm : {} \n {} \n {}\n {}" ,fileShardMapping,fileIp,filePort,filePath);
        FileShardMapping fileShardMapping1 = JSON.parseObject(fileShardMapping, FileShardMapping.class);
        fileShardService.getFile(fileShardMapping1,fileIp,filePort,offset,filePath);
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setCode(200);
        resultVo.setMessage("fileId : " + fileShardMapping1.getFileId() +"   fileIndex:" + fileShardMapping1.getShardIndex() + " finished");
        return new ResultVo<>();
    }

    /**
     * 被动，根据参数，返回文件流
     *
     * @param path 文件路径
     * @param response 响应体
     * @date 2024/3/3 15:42
     */

    @GetMapping("/sendFile")
    public void sendFile(@RequestParam("path") String path ,
                        HttpServletResponse response) throws IOException {
        //index代表的是分片编号，从0开始

        logger.info("文件发送{}",path );

        File file = new File(path);
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        response.setContentType(mimeType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");


        response.setContentLength(SystemConstants.shadeSize);

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());


        byte[] buffer = new byte[SystemConstants.shadeSize];
        inputStream.read(buffer);
        outputStream.write(buffer, 0, SystemConstants.shadeSize);

        outputStream.close();
        inputStream.close();
    }

    private int FileMaxChunk(File file){
        return (int)Math.ceil(file.length() * 1.0 / SystemConstants.shadeSize );
    }

}
