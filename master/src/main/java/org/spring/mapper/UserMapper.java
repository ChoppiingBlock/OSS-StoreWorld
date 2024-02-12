package org.spring.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.spring.po.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
