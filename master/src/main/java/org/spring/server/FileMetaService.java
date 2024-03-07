package org.spring.server;


import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.FileMetadata;

@Mapper
public interface FileMetaService extends IService<FileMetadata> {
}

