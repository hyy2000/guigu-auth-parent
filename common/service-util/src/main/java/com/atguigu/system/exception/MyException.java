package com.atguigu.system.exception;

import com.atguigu.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class MyException extends RuntimeException{
    private Integer code;
    private String message;

    public MyException() {
    }
    public MyException(Integer code) {
        this.code = code;
        this.message = "自定义异常";
    }

    public MyException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MyException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
//        this.message = "自定义异常";
    }


}
