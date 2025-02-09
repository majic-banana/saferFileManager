package com.project.saferfilemanager.common;

public enum ResultCode2Msg {
    SUCCESS("200", "成功"),

    PARAM_ERROR("400", "参数异常"),
    AUTHENTICATION_ERROR("401", "认证失败，请重新登录"),
    AUTHORIZATION_ERROR("402","权限不足"),
    PARAM_LOST_ERROR("4001", "参数缺失"),

    SYSTEM_ERROR("500", "系统异常"),
    USER_EXIST_ERROR("5001", "用户名已存在"),
    USER_NOT_LOGIN("5002", "用户未登录"),
    USER_ACCOUNT_ERROR("5003", "账号或密码错误"),
    USER_NOT_EXIST_ERROR("5004", "用户不存在"),
    PARAM_PASSWORD_ERROR("5005", "原密码输入错误"),
    FILE_NOT_FOUND("5006", "找不到文件"),
    FILE_UPLOAD_ERROR("5007","upload failed"),
    TYPE_NOT_SUPPORT("5007", "该文件类型不支持预览"),
    BAD_PATH("5008", "恶意路径"),
    USER_UNMATCHED("601","用户不匹配");

    public String code;
    public String msg;

    ResultCode2Msg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
