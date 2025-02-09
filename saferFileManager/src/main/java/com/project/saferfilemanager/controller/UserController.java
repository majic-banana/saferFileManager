package com.project.saferfilemanager.controller;


import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RequestMapping
@RestController
public class UserController {

    @Resource
    UserService userService;


    @GetMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        //获取会话，如果没有就创建
        HttpSession session = request.getSession(true);

        return userService.login(username, password, session);
    }


    @PostMapping("/register")
    public Result register(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        return userService.register(username, password, email);
    }

}
