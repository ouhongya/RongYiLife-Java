package com.rysh.module.clientLoginRegister.service;

import com.rysh.module.clientLoginRegister.beans.User;

import java.util.Map;

public interface LoginAndRegisterService {
//登陆注册
Map<String,Object> login(String phoneNum, String code) throws Exception;
//发送验证码短信
    String sendMessage(String loginName) throws Exception;
//查询当前用户信息
    User findNowUser(String uid) throws Exception;
//编辑用户信息
    void updateUserById(User user) throws Exception;


    void updateUserAvatar(String uid, String url) throws Exception;
}
