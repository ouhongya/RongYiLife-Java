package com.rysh.module.serverSystem.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Log4j2
@RequestMapping(value = "/client/redis")
@RestController
public class redisTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping(value = "/redisSet")
    public String redisSet() {
        //stringRedisTemplate.opsForValue().set("aaddd","123456");
        redisTemplate.opsForValue().set("aaaddd","嘻嘻");
       return "aaaa";
    }

    @GetMapping(value = "/redisGet")
    public String redisGet() {
        try {
            String aaddd = stringRedisTemplate.opsForValue().get("aaddd");
            return aaddd;
        }catch (Exception e){
            log.error("r错");
            log.error(e);
            return null;
        }
        //return (String) redisTemplate.opsForValue().get("aaaddd");
    }
}
