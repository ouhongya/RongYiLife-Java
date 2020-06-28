package com.rysh.module.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class CheckRedisTokenUtils {
    @Autowired
    private  RedisTemplate redisTemplate;

    public  String checkRedisToken(String token){
        //从redis中获取未加密的token
        byte[] o = (byte[]) redisTemplate.opsForValue().get(token.getBytes());
        if(o!=null){
            String realToken=new String(o);
            //校验token
            Map<String, Object> tokenMap = TokenUtils.checkToken(realToken);
            String state= (String) tokenMap.get("state");
            if("校验成功".equals(state)){
                Map dataMap = (Map) tokenMap.get("data");
                return (String) dataMap.get("uid");
            }else {
                //token过期  清除redis中的token信息
                redisTemplate.delete(token.getBytes());
                return null;
            }
        }else {
            return null;
        }
    }
}
