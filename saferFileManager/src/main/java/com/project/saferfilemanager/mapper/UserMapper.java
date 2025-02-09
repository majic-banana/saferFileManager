package com.project.saferfilemanager.mapper;


import com.project.saferfilemanager.entity.User;

import java.util.List;


public interface UserMapper {

    User getUserById(int id);

    User getUserByUsername(String name);

    List<User> getUsersByRole(String role);

    List<User> getAllUsers();


    void insertUser(User user) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(User user) throws Exception;

}
