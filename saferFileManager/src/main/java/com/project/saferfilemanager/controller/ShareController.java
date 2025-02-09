package com.project.saferfilemanager.controller;


import com.github.pagehelper.PageInfo;
import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.entity.Share;
import com.project.saferfilemanager.mapper.ShareMapper;
import com.project.saferfilemanager.service.FileService;
import com.project.saferfilemanager.service.ShareService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RequestMapping("/share")
@RestController
public class ShareController {

    @Resource
    private ShareService shareService;

    @Resource
    private FileService fileService;

    @Resource
    private ShareMapper shareMapper;

    @GetMapping("/addShare")
    public Result addShare(@RequestParam String path, @RequestParam(defaultValue = "7200000") long liveTime, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        int userId = (Integer) session.getAttribute("userId");

        return shareService.addShare(path,liveTime,userId);

    }



    @GetMapping("/download")
    public void download(@RequestParam String hashName, HttpServletResponse response) throws Exception{
        String fullPath = ShareService.rootPath + '/' + hashName;
        Share share = shareMapper.getShareByName(hashName);
        if(share != null ){
            fileService.download(fullPath, response,share.getOriName());
        }else{
            response.sendError(4444,"文件不存在");
        }

    }



}
