package com.boot.shiro.config;

import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//定义该类为全局异常处理类，可以指定处理的controller范围

@RestControllerAdvice(basePackages = {"com.boot.shiro"})
public class ShiroException {

    private static Logger logger = LoggerFactory.getLogger(ShiroException.class);

    @ExceptionHandler(AuthorizationException.class)
    public String authorizationException (AuthorizationException e){
//        logger.error("抱歉您没有权限访问该内容，请先登录");
        return "抱歉您没有权限访问该内容，请先登录!";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
//        logger.error("系统异常",e);
        return "系统异常!";
    }

    @ExceptionHandler(UnknownAccountException.class)
    public String UnknownAccountException(UnknownAccountException e){
//        logger.error("账号或密码不正确",e);
       return "账号或密码不正确";
    }

}