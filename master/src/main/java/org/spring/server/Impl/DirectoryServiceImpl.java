package org.spring.server.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spring.mapper.DirectoryMapper;
import org.spring.po.DirectoryMetadata;
import org.spring.server.DirectoryService;
import org.springframework.stereotype.Service;

@Service
public class DirectoryServiceImpl extends ServiceImpl<DirectoryMapper , DirectoryMetadata> implements DirectoryService {
}
