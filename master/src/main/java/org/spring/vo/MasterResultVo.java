package org.spring.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MasterResultVo {
    private String code;
    private String message;

    @Override
    public String toString(){
        return "code : " + code + "\n message: " + message;
    }
}
