package com.rysh.module.mobileUser.beans;


import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;

@Data
public class User {
    //用户UUID
    private String  id;
    //用户手机号码
    private String phone;
    //用户是否激活
    private int isActive;
    //用户住址
    private String address;
    //用户头像
    private String avatar;
    //用户积分
    private BigInteger score;
    //用户注册时间
    private Timestamp createdTime;
    //用户最后更新时间
    private Timestamp lastedUpdateTime;
}
