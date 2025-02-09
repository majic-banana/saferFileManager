package com.project.saferfilemanager.controller;

import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.entity.User;
import com.project.saferfilemanager.service.AdminService;

import com.project.saferfilemanager.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/showAllUsers")
    public Result showAllUsers() {

        try{
            List<User> users = users = adminService.showAllUsers();
            return Result.success(users);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }




    }

    @PostMapping("/addUser")
    public Result addUser(@RequestBody User user) {
        try {

            user.setPassword(UserService.hashPassword(user.getPassword()));

            adminService.addUser(user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody User user) {

        try {
            adminService.deleteUser(user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        try {
            user.setPassword(UserService.hashPassword(user.getPassword()));
            adminService.updateUser(user);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }
    }
}
