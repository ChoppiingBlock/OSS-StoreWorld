package org.spring.server.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spring.mapper.UserMapper;
import org.spring.po.User;
import org.spring.server.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    public List<User> getAllUsers() {
        return baseMapper.selectList(null);
    }
}
