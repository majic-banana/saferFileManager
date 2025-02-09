package com.project.saferfilemanager.common;

public enum Roles {
    ADMIN(0),
    USER(1);


    public final int seq;


    Roles(final int seq) {
        this.seq = seq;
    }
}
