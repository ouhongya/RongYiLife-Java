package com.rysh.module.clientOrders.beans;

import lombok.Data;

import java.util.Date;

@Data
public class ScoreUser {
    private String id;    //主键id
    private String userId;   //用户id
    private String ordersSplitId;   //子订单id
    private Integer score;   //分数
    private Date createdTime;
}
