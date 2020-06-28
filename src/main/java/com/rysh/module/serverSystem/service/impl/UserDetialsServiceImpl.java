package com.rysh.module.serverSystem.service.impl;

import com.rysh.module.serverSystem.beans.Function;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UserDetialsServiceImpl implements UserDetailsService {



    @Autowired
    private AccountMapper accountMapper;


    /**
     *
     * @param s   用户名
     * @return     登陆成功返回user对象
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {


        List<GrantedAuthority> accList;
        //通过username查询用户信息
        User user = null;
        try {
            user = accountMapper.findUserByUsername(s);
        } catch (Exception e) {
            log.error("登陆异常");
            log.error(e);
        }
        if(user!=null&&user.getStatus()==1){
            //username输入正确
            //权限集合
            accList=new ArrayList<>();
            //通过用户名查询权限
            List<String> urls = new ArrayList<>();
            try {
                List<Function> funs = accountMapper.findFunctionByUserNameForPower(s);
                for (Function fun : funs) {
                    urls.add(fun.getFunctionUrl());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            for (String url : urls) {
                //向权限集合添加权限
                accList.add(new SimpleGrantedAuthority(url));
            }
            //返回security提供的user对象
            org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),accList);
            return user1;
        }
        return null;
    }
}
