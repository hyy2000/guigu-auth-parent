package com.atguigu.system.handler;

import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.fail(e.getMessage()).message(e.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(value = ArithmeticException.class)
    public Result handleArithException(ArithmeticException e){
        e.printStackTrace();
        return Result.fail("算术异常");
    }
}
