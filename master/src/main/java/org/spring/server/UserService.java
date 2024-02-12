package org.spring.server;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Mapper;
import org.spring.po.User;
@Mapper
public interface UserService extends IService<User> {
}
