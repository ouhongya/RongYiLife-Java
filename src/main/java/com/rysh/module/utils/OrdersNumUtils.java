package com.rysh.module.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class OrdersNumUtils {
    @Autowired
    private  RedisTemplate redisTemplate;

   private  SimpleDateFormat  sf=new SimpleDateFormat("yyyyMMdd");

    public  String createOrdersNum(int city,int area){
        //构建3位数  城市码
        String cityNum = String.format("%3d", city).replace(" ","0");
        //构建2位数  区域码
        String areaNum = String.format("%2d", area).replace(" ", "0");
        //时间码
        String timeNum=sf.format(new Date());
        //从redis中获取当天下单数
        byte[] ordersTimesByte= (byte[]) redisTemplate.opsForValue().get("numberOfOrders".getBytes());
        String ordersTimesStr=new String(ordersTimesByte);
        int ordersTimes = Integer.parseInt(ordersTimesStr);
        //更新当天下单次数
        redisTemplate.opsForValue().set("numberOfOrders".getBytes(),String.valueOf(ordersTimes+1).getBytes());
        //构建4位数 当天下单数
        String ordersTimesNum=String.format("%4d",ordersTimes).replace(" ","0");
        String ordersNum=cityNum+areaNum+timeNum+ordersTimesNum;
        String realOrdersNum = ordersNum.trim();

        return realOrdersNum;
    }



}
