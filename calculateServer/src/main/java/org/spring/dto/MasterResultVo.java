package org.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MasterResultVo<T>  {
    private int code;
    private String message;
    private T object;

    @Override
    public String toString(){
        return "code : " + code + "\n message: " + message;
    }
}
