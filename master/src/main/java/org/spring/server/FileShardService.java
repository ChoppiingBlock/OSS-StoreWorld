package org.spring.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.FileMetadata;
import org.spring.po.FileShardMapping;
@Mapper
public interface FileShardService extends IService<FileShardMapping> {
    /**
     * 创建mapping任务，在确保store完成下载后，写数据库
     *
     * @param fileIp client ip
     * @param filePort client port
     * @param filePath client fIle path
     * @return boolean
     * @date 2024/2/21 10:45
     */

    public boolean sendMetadataToStore(FileMetadata fileMetadata
            ,String fileIp
            , String filePort
            , String filePath);
}
