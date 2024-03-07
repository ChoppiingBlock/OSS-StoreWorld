package org.spring.server.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spring.mapper.FileMetadataMapper;
import org.spring.po.FileMetadata;
import org.spring.server.FileMetaService;
import org.springframework.stereotype.Service;

@Service
public class FileMetaServiceImpl extends ServiceImpl<FileMetadataMapper, FileMetadata> implements FileMetaService {


}
