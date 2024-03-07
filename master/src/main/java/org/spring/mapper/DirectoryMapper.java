package org.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.DirectoryMetadata;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryMapper extends BaseMapper<DirectoryMetadata> {
}
