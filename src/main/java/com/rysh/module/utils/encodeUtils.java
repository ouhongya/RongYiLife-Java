package com.rysh.module.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 加密工具类
 */
@Component
public class encodeUtils {

    private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();

    public String encode(String data){
        return data;
    }
}
