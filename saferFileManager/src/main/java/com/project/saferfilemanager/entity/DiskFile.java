package com.project.saferfilemanager.entity;

import java.util.ArrayList;


public class DiskFile {
    private Integer id;
    private String name;
    private String path;  //相对路径
    private String type;
    private Integer size;
    private String owner;


    private ArrayList<DiskFile> children;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public Integer getSize() {
        return size;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ArrayList<DiskFile> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<DiskFile> children) {
        this.children = children;
    }

}
