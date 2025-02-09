package com.project.saferfilemanager.controller;


import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.common.Roles;
import com.project.saferfilemanager.common.security.SecurtyUtils;
import com.project.saferfilemanager.service.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;




@RequestMapping("/file")
@RestController
public class FileController {


    @Resource
    private FileService fileService;

    @GetMapping("/selectAll")
    Result selectAll(HttpServletRequest request) {
        HttpSession session = request.getSession();


        String role = (String) session.getAttribute("role");
        int seq = Roles.valueOf(role).seq;

        //如果权限为ADMIN则获取所有文件
        String userName =(seq == 0) ? "/" : ('/' + (String) session.getAttribute("userName"));



        return fileService.selectAll(userName);
    }

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("path") String path, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");

        // 构建完整的上传路径
        path = '/' + userName + path;

        try {
            fileService.upload(path, file);
            return Result.success("文件上传成功！"); // 返回成功的结果
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultCode2Msg.FILE_UPLOAD_ERROR); // 返回失败的结果
        }
    }


    @GetMapping("/download")
    public void download(@RequestParam String path, HttpServletResponse response) {
        String fullPath = FileService.rootPath + '/' + path;
        fileService.download(fullPath, response, null);
    }


    @GetMapping("/delete")
    Result delete(@RequestParam String path) {
        String fullPath = FileService.rootPath + '/' + path;
        return fileService.delete(fullPath);

    }
}

