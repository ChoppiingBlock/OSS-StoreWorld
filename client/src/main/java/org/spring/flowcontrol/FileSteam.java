package org.spring.flowcontrol;


import cn.hutool.core.io.FileUtil;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.fastjson.JSON;
import org.spring.Feign.MasterFeignClient;
import org.spring.po.DirectoryMetadata;
import org.spring.po.FileMetadata;
import org.spring.service.FileService;
import org.spring.service.UserService;
import org.spring.tool.Impl.CodecMd5Util;
import org.spring.dto.FileContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public  class FileSteam {
    @Autowired
    UserService userService;

    @Autowired
    FileContext fileContext;

    @Autowired
    FileService fileService;

    @Autowired
    CodecMd5Util codecMd5Util;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;


    public static void ErrorTrip(String s){
        System.out.println(s);
    }

    public static void SuccessTrip(){
        System.out.println("成功");
    }

    public static void cleanCMD(){
        for (int i = 0; i < 50; i++) {
            System.out.println(); // 输出空行
        }
    }

    public void printfFileList() throws IOException {
        fileContext.setNowDirList(JSON.parseArray(fileService.viewDirectory
                (fileContext.getParentId(), fileContext.getUserId()),DirectoryMetadata.class));
        fileContext.setNowFileList(JSON.parseArray(fileService.viewFileList
                (fileContext.getParentId(), fileContext.getUserId()),FileMetadata.class));

        System.out.println("now path is : " + fileContext.getNowPath());
        for (int i = 0; i < fileContext.getNowDirList().size(); i++) {
            System.out.println("Dir " + fileContext.getNowDirList().get(i).getName());
        }
        for (int i = 0; i < fileContext.getNowFileList().size(); i++) {
            System.out.println("Fil " + fileContext.getNowFileList().get(i).getName());
        }
    }

    private void addDir(String input){
        System.out.println("input directory name:");
        String name = input.split(" ")[1];
        int parentId = fileContext.getParentId();
        int userId = fileContext.getUserId();
        String path = fileContext.getNowPath() + "/" + name;
        fileService.addDir(name,parentId,userId,path);
    }

    public static boolean uploadFile(String filePath , String fileType , String shadeType , int m , int n){

        return true;
    }

    public static boolean updateFile(String oldFileName, String newFileName, Scanner scanner){
        System.out.printf("你确定将 %s 文件改为 %s 吗？%n", oldFileName, newFileName);
        String input = scanner.next();
        return input.equals("y") || input.equals("Y");
    }

    public void cdOrder(String param){
        String dirName = param.split(" ")[1];

        if(dirName.equals("..")){
            int length = fileContext.getPathNameList().size() - 1;
            if(length > 0){
                fileContext.getPathNameList().remove(length);
                fileContext.getPathIdList().remove(length);
                fileContext.setNowFileList(new ArrayList<>());
                fileContext.setNowFileList(new ArrayList<>());
            }

        }

        for (int i = 0; i < fileContext.getNowDirList().size(); i++) {
            if(dirName.equals(fileContext.getNowDirList().get(i).getName())){
                fileContext.getPathNameList().add(dirName);
                fileContext.getPathIdList().add(fileContext.getNowDirList().get(i).getId());
                fileContext.setNowFileList(new ArrayList<>());
                fileContext.setNowFileList(new ArrayList<>());
            }
        }
    }

    public void addFile(String input) {
        String filePath = input.split(" ")[1];
        if(FileUtil.exist(filePath)){
            System.out.println("没有这个文件");
        }
        File file = new File(filePath);

        FileMetadata fileMetadata = FileMetadata.builder()
                .name(FileUtil.getName(file))
                .size(file.length())
                .md5(codecMd5Util.getFileMd5(file))
                .type(FileUtil.extName(file))
                .parentId(fileContext.getParentId())
                .path(fileContext.getNowPath() + "/" + file.getName())
                .shadeType(input.split(" ")[2]).build();
        // 获取当前服务的IP和端口
        String ip = nacosDiscoveryProperties.getIp();
        int port = nacosDiscoveryProperties.getPort();
        fileService.addFile(JSON.toJSONString(fileMetadata),ip , String.valueOf(port),filePath);
    }

    public void downloadFile(String input){
        String fileNameOrId = input.split(" ")[1];
        for(FileMetadata fileMetadata : fileContext.getNowFileList()){
            if(fileNameOrId.equals(fileMetadata.getName())){
                fileService.downloadFile(fileMetadata.getId());
                break;
            }
        }
    }

    public void fileSteam(Scanner scanner) throws IOException {
        boolean flag = true;
        while(flag){
            printfFileList();
            String input = scanner.nextLine();
            String order = "";
            try {
                order = input.split( " ")[0];
            } catch (Exception e){
                ErrorTrip("请输入正确的命令，输入help查看命令");
            }
            switch (order) {
                case("cd") : {
                    cdOrder(input);
                    break;
                }
                case("mkdir") : {
                    addDir(input);
                    cleanCMD();
                    printfFileList();
                    break;
                }

                case("touch") : {
                    addFile(input);
                    cleanCMD();
                    printfFileList();
                    break;
                }

                case("download") : {
                    downloadFile(input);
                    cleanCMD();
                    printfFileList();
                    break;
                }
            }
        }
    }


}
