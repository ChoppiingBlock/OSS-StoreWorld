package org.spring.dto;
public enum StatusCode {
    SUCCESS(200),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;


    StatusCode(int code) {
        this.code = code;

    }

    public int getCode() {
        return code;
    }


}