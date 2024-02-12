package org.spring.mapper;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.User;
@Mapper

public interface PermissionMapper extends IService<User> {
}
