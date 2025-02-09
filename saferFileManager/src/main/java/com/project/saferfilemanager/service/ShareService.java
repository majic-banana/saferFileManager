package com.project.saferfilemanager.service;


import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.project.saferfilemanager.common.Result;
import com.project.saferfilemanager.common.ResultCode2Msg;
import com.project.saferfilemanager.entity.Share;
import com.project.saferfilemanager.mapper.ShareMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ShareService {

    @Resource
    private final ShareMapper shareMapper;

    public  static final String rootPath = "E:/Project/saferFileManager/shares";
    private static final String diskRootPath = "E:/Project/saferFileManager/disk";

    public ShareService(ShareMapper shareMapper) {
        this.shareMapper = shareMapper;
    }



    public Result addShare(String path, long liveTime, int userId) {
        path.replace("\\","/");
        String extname = path.substring(path.lastIndexOf("."));
        String name = hashName(path) + extname;

        long currentTime = DateUtil.current();
        Share share = new Share();

        share.setShareTime(String.valueOf(currentTime));
        share.setEndTime(String.valueOf(currentTime + liveTime));
        share.setName(name);
        share.setUserId(userId);
        share.setOriName(path.substring(path.lastIndexOf("\\") + 1));


        //完整disk路径
        path = diskRootPath + '/' + path;
        File fromFile = new File(path);
        File toFile = new File(rootPath + '/' +name);

        try{
            Files.copy(fromFile.toPath(), toFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            shareMapper.insertShare(share);
        }catch (Exception e){
            e.printStackTrace();
            return Result.error(ResultCode2Msg.SYSTEM_ERROR);
        }

        //toDo: 返回一个下载连接
        return Result.success(name);

    }











    private String hashName(String name) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(name.getBytes());
            return Base64.getEncoder().encodeToString(hash); // 将哈希值编码为字符串
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
