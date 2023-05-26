package com.jinstagram.global.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private String code;
    private String message;
    private T data;
    private PageInfo pageInfo;
    public Result (T data){
        this.code = "200";
        this.message = "";
        this.data = data;
    }
    public Result (T data, PageInfo pageInfo){
        this.code = "200";
        this.message = "";
        this.data = data;
        this.pageInfo = pageInfo;
    }

}
