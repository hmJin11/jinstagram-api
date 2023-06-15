package com.jinstagram.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private String code;
    private String message;
    private T data;
    public Result (T data){
        this.code = "200";
        this.message = "";
        this.data = data;
    }
}
