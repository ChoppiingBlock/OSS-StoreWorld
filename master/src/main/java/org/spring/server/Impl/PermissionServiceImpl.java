package org.spring.server.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spring.mapper.PermissionMapper;
import org.spring.po.Permission;
import org.spring.server.PermissionService;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
}
