package com.jchen.shiro_demon.service;


import com.jchen.shiro_demon.mapper.UserMapper;
import com.jchen.shiro_demon.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
