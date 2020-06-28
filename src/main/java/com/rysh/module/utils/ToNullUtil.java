package com.rysh.module.utils;

/**
 * 判断json字符串是否为空
 * @param null
 * @return
 * @author Hsiang Sun
 * @date 2019/11/9 14:43
 */
public class ToNullUtil {
    public static String toNull(String jsonStr){
        if ("null".equals(jsonStr) || "undefined".equals(jsonStr) || "".equals(jsonStr)){
            return null;
        }else {
            return jsonStr;
        }
    }
}
