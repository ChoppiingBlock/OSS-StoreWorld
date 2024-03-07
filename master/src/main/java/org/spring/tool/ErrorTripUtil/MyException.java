package org.spring.tool.ErrorTripUtil;

import lombok.Getter;

@Getter
public class MyException extends Exception{
    private int errorCode;

    public MyException(int errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public MyException(){

    }

}

