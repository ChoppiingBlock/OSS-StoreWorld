package org.spring.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.DirectoryMetadata;
@Mapper
public interface DirectoryService extends IService<DirectoryMetadata>{
}
