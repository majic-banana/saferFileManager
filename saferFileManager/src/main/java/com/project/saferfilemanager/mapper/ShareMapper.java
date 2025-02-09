package com.project.saferfilemanager.mapper;

import com.project.saferfilemanager.entity.Share;

import java.util.List;

public interface ShareMapper {

    public Share getShareById(int id);
    public Share getShareByName(String name);
    public List<Share> getShareByUserId(int userId);
    public List<Share> getAllShare();




    public void insertShare(Share share) throws Exception;
    public void deleteShareById(int id) throws Exception;

}
