package com.project.saferfilemanager.common.security;

public class SecurtyUtils {

    public static boolean isPathValid(String username, String path){
        if(path == null){
            return true;
        }
        return path.startsWith(username) && !path.contains("..");
    }

}
