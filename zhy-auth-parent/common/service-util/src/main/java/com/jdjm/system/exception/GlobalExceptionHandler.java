package com.jdjm.system.exception;


import com.jdjm.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    //捕获全局异常
    @ExceptionHandler(Exception.class)
    //注意这里要以JSON的形式返回
    @ResponseBody
    public Result<?> func1(Exception e){
        System.out.println("全局异常");
        e.printStackTrace();
        return Result.fail();
    }

    //捕获特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result<?> func2(Exception e){
        System.out.println("特定异常");
        e.printStackTrace();
        return Result.fail().message("内部错误");
    }


    //捕获自定义错误
    @ExceptionHandler(JdjmException.class)
    @ResponseBody
    public Result<?> func3(JdjmException e){
        System.out.println("自定义异常");
        e.printStackTrace();
        //链式编程
        return Result.fail().code(e.getCode()).message(e.getMessage());
    }

}
