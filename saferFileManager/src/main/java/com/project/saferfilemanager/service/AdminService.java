package com.project.saferfilemanager.service;

import com.project.saferfilemanager.entity.User;
import com.project.saferfilemanager.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AdminService {

    @Resource
    private UserMapper userMapper;

    // 获取所有用户
    public List<User> showAllUsers() {
        return userMapper.getAllUsers();
    }

    // 添加用户
    public void addUser(User user) throws Exception {

        userMapper.insertUser(user);
    }

    // 删除用户
    public void deleteUser(User user) throws Exception {
        userMapper.deleteUser(user);
    }

    // 更新用户
    public void updateUser(User user) throws Exception {
        userMapper.updateUser(user);
    }
}
