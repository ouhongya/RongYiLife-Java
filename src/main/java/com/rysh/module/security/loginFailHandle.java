package com.rysh.module.security;


import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Log4j2
public class loginFailHandle implements AuthenticationFailureHandler {
    @Autowired
    private AccountService accountService;
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //登陆失败处理
        //获取用户名
        String username = httpServletRequest.getParameter("username");
        //查询用户状态码
        User user = null;
        try {
            user = accountService.findUserByUsername(username);
        } catch (Exception ex) {
            log.error("登陆失败类 查询用户异常");
            log.error(ex);
        }
        //不同的状态码返回不同的提示信息
        if(user!=null){
            if(user.getStatus()==0){
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().println("该账号已被禁用!");
            }
            if(user.getStatus()==1){
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().println("用户名或密码错误!");
            }
        }else {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().println("用户名或密码错误!");
        }

    }
}
