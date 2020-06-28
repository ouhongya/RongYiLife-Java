package com.rysh.module.mobileUser.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
public class UserInfo {
    //用户手机号码
    private String phone;
    //用户是否激活
    private int is_active;
    //用户头像
    private String avatar;
    //用户积分
    private BigInteger score;
    //用户创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date created_time;
    //栋
    private String building;
    //单元
    private String unit;
    //室
    private String room;
}
