package com.jchen.shiro_demon.service;

import com.jchen.shiro_demon.model.User;

public interface UserService {
    User findByUsername(String username);
}
