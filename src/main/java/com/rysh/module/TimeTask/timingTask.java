package com.rysh.module.TimeTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class timingTask {
    @Autowired
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRedisOrdersTime(){
     redisTemplate.opsForValue().set("numberOfOrders".getBytes(),"1".getBytes());
    }
}
