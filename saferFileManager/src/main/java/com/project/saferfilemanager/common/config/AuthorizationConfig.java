package com.project.saferfilemanager.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "authorization")
public class AuthorizationConfig {

    private Map<String, String> path2role = new HashMap<>();

    public Map<String, String> getPath2role() {
        return path2role;
    }

    public void setPath2role(Map<String, String> path2role) {
        this.path2role = path2role;
    }
}
