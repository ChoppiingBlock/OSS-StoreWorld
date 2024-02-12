package org.spring.mapper;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.FileMetadata;

@Mapper
public interface FileMetadataMapper extends IService<FileMetadata> {
}
