package org.spring.tool.ErrorTripUtil;

public enum ErrorCode {

    FALSE(0,"错误"),
    FILE_ERROR(1001, "文件错误"),
    FILE_NOT_EXIST(1002, "文件不存在"),
    BLOCKING_PARAM_ERROR(1003, "分块参数错误"),
    ERROR_PROXY(1004, "代理对象异常");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

