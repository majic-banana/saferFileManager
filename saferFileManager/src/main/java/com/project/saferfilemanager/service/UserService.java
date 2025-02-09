package com.project.saferfilemanager.service;


import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.common.Roles;
import com.project.saferfilemanager.entity.User;
import com.project.saferfilemanager.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    public Result login(String username, String password, HttpSession session) {

        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            return Result.error(ResultCode2Msg.USER_EXIST_ERROR);
        }

        String storedHash = user.getPassword(); // 假设密码已哈希存储
        // 计算输入密码的哈希值
        String hashedPassword = hashPassword(password);
        if (hashedPassword.equals(storedHash)) {
            //记录会话
            session.setAttribute("userName", user.getName());
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRole());


            return Result.success(user);
        } else {

            return Result.error(ResultCode2Msg.USER_ACCOUNT_ERROR);
        }
    }

    // 用于哈希密码的方法
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash); // 将哈希值编码为字符串
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public Result changePassword(String username, String newPassword) {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            return Result.error(ResultCode2Msg.USER_NOT_EXIST_ERROR);
        }

        newPassword = hashPassword(newPassword);
        user.setPassword(newPassword);
        try{
            userMapper.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }

        return Result.success(ResultCode2Msg.SUCCESS);



    }


    public Result register(String username, String password, String email) {
        //查看是否重名
        User test = userMapper.getUserByUsername(username);
        if (test != null) {
            return Result.error(ResultCode2Msg.USER_EXIST_ERROR);
        }


        User user = new User();
        user.setName(username);

        password = hashPassword(password);
        user.setPassword(password);

        //注册接口永远是USER
        user.setRole(Roles.USER.toString());

        if(email != null && !email.isEmpty()){
            user.setEmail(email);
        }
        try{
            userMapper.insertUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }

        return Result.success(ResultCode2Msg.SUCCESS);
    }
}
