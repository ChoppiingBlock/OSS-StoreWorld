package org.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultVo<T>  {
    private int code = 0;
    private String message = "";
    private T object = null;

    public ResultVo() {

    }

    @Override
    public String toString(){
        return "code : " + code + "\n message: " + message;
    }
}
