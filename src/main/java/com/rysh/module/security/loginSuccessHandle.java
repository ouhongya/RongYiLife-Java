package com.rysh.module.security;

import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class loginSuccessHandle extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private AccountMapper accountMapper;

    //后台用户 token过期时间
    @Value("${result.serverExpirationTime}")
    private Integer serverExpirationTime;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        String username = authentication.getName();
        //登陆成功
        //去除字符串两边的空格
        username=username.trim();
        //token必要数据
        Map<String,Object> userTokenMap=new HashMap<>();
        //用户名
        userTokenMap.put("uid",username);
        //通过用户名查询用户
        User user = accountMapper.findUserByUsername(username);
        //用户类型 0内部  1商铺主 2农场主 3农庄主
        userTokenMap.put("isInsider",user.getIsInsider());
        //token生成时间
        userTokenMap.put("iat",new Date().getTime());
        //token过期时间
        userTokenMap.put("ext",new Date().getTime()+serverExpirationTime);
        //为用户创建token
        String token = TokenUtils.createToken(userTokenMap);
        //将token设置到响应头里面
        response.setHeader("token",token);

        response.getWriter().println(1);
    }
}
