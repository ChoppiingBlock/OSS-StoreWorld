package org.spring.server.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spring.controller.FileController;
import org.spring.dto.ResultVo;
import org.spring.dto.StatusCode;
import org.spring.dto.SystemConstants;
import org.spring.mapper.FileShardMapper;
import org.spring.po.FileMetadata;
import org.spring.po.FileShardMapping;
import org.spring.server.FileShardService;
import org.spring.tool.FileTransTool;
import org.spring.tool.NacosUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileShardServiceImpl extends ServiceImpl<FileShardMapper, FileShardMapping> implements FileShardService {

    Logger logger = LoggerFactory.getLogger(FileShardServiceImpl.class);

    @Autowired
    DiscoveryClient discoveryClient;

    public boolean sendMetadataToStore(FileMetadata fileMetadata
            ,String ClientFileIp
            ,String ClientFilePort
            ,String ClientFilePath) {
        List<FileShardMapping> fileShardMappings = new ArrayList<>();
        if(fileMetadata.getShadeType().equals("1")){
            //每个分块的数据
            for (int i = 0; i < FileTransTool.MaxChunkNumber(fileMetadata.getSize(), 1024 * 64); i++) {
                FileShardMapping fileShardMapping = FileShardMapping.builder()
                        .fileId(fileMetadata.getId())
                        .shardIndex(i).build();
                fileShardMappings.add(fileShardMapping);
            }
            try {
                allocationAlgorithms(fileShardMappings,fileMetadata.getShadeType(),ClientFileIp,ClientFilePort,ClientFilePath);
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }

        }
        return true;
    }

    private List<String> allocationAlgorithms(List<FileShardMapping> fileShardMappings , String params
            ,String ClientFileIp
            ,String ClientFilePort
            ,String ClientFilePath) throws NacosException {
        NamingService namingService = NacosFactory.createNamingService("127.0.0.1:8848");

        String serviceName = "storeServer";
        List<Instance> instances = namingService.getAllInstances(serviceName);

        int instancesSize = instances.size();
        Random random = new Random();
        for (int i = instancesSize - 1; i >= 0 ; i--) {
            int j = random.nextInt(i + 1);
            Instance temp = instances.get(i);
            instances.set(i,instances.get(j));
            instances.set(j,temp);
        }
        //调用算法给参数。
        for (int i = 0; i < fileShardMappings.size(); i++) {
            fileShardMappings.get(i).setShardStoreServerId(instances.get(i % instances.size()).getMetadata().get(SystemConstants.serverId));
            getFeign(fileShardMappings.get(i),ClientFileIp,ClientFilePort,ClientFilePath);
        }

        return new ArrayList<>();
    }

    private void getFeign(FileShardMapping fileShardMapping
            ,String clientFileIp
            ,String clientFilePort
            ,String clientFilePath)  {
        Map<String , String> param = new HashMap<>();
        param.put("fileShardMapping", JSON.toJSONString(fileShardMapping));
        param.put("fileIp" , clientFileIp);
        param.put("filePort", clientFilePort);
        param.put("offset" , "0");
        param.put("filePath", clientFilePath);
        FileTransTool fileTransTool = new FileTransTool();

        logger.info("try to use store Api ,params : {}\n{}\n{}\n{}" , fileShardMapping,clientFileIp,clientFilePort,clientFilePath);

        try {
            List<String> ipPort = NacosUtil.getIpPort(discoveryClient,SystemConstants.storeServerName, fileShardMapping.getShardStoreServerId());

            Response r  = fileTransTool.getHttpResult(ipPort.get(0),ipPort.get(1), "/storeServer/getShade",param);
            if (r.body() != null) {
                ResultVo<Objects> resultVo = JSON.parseObject(r.body().string(),ResultVo.class ) ;
                if(resultVo.getCode() == StatusCode.SUCCESS.getCode()){
                    logger.info("success : {}",resultVo.getMessage());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
