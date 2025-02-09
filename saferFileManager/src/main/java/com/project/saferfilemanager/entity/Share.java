package com.project.saferfilemanager.entity;

import java.util.Date;

public class Share {
    private int id;
    private String shareTime;
    private String endTime;
    private int userId;
    private String name;
    private String oriName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriName() {
        return oriName;
    }

    public void setOriName(String oriPath) {
        this.oriName = oriPath;
    }
}
