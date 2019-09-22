package com.jchen.shiro_demon.mapper;

import com.jchen.shiro_demon.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByUsername(@Param("username")String username);


}
