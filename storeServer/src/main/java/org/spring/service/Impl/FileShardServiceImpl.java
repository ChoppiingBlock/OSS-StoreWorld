package org.spring.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spring.dto.SystemConstants;
import org.spring.mapper.FileShardMapper;
import org.spring.po.FileShardMapping;
import org.spring.service.FileShardService;
import org.spring.tool.FileTransTool;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileShardServiceImpl extends ServiceImpl<FileShardMapper, FileShardMapping> implements FileShardService {


    @Override
    public boolean getFile(FileShardMapping fileShardMapping, String fileIp, String filePort,int offset, String filePath) {
        Map<String , String> params = new HashMap<>();
        params.put("path", filePath);
        params.put("index", String.valueOf(fileShardMapping.getShardIndex()));
        params.put("offset","0");
        fileShardMapping.setShardLocation(SystemConstants.testStorePath +  "\\" + fileShardMapping.getFileId() + "." + fileShardMapping.getShardIndex());
        FileTransTool fileTransTool = new FileTransTool();


        File directory = new File(SystemConstants.testStorePath);
        if (!directory.exists()) { // 检查文件夹是否存在
            if (directory.mkdir()) { // 创建文件夹
                System.out.println("文件夹创建成功！");
            } else {
                System.out.println("文件夹创建失败！");
            }
        }

        try {
            fileTransTool.getHttpFileInStream(fileIp,filePort,"/FileController/sendFile",params,fileShardMapping.getShardLocation());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return writeDataBase(fileShardMapping);
    }

    @Override
    public boolean writeDataBase(FileShardMapping fileShardMapping) {
        return baseMapper.insert(fileShardMapping) > 0;
    }
}
