package com.gszcn.security.exception;

import com.gszcn.security.until.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @author leiyunlong
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(ServiceException.class)
    @ResponseBody //为了返回数据
    public R error(ServiceException e) {
        e.printStackTrace();
        return R.error().msg(e.getMessage());
    }

}
