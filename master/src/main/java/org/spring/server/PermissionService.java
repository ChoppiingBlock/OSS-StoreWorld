package org.spring.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.Permission;

@Mapper
public interface PermissionService extends IService<Permission> {
}
