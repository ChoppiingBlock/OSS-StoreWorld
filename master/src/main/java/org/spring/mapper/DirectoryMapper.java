package org.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.DirectoryMetadata;

@Mapper
public interface DirectoryMapper extends BaseMapper<DirectoryMetadata> {
}
