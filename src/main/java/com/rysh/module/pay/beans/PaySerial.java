package com.rysh.module.pay.beans;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
/*
*
* 支付流水
* */
public class PaySerial {
    private String id;
    private String ordersId;
    private Date payTime;
    private BigDecimal ordresFee;
    private BigDecimal payFee;
    private int state;//1：微信返回信息和订单信息符合 0：返回信息和订单信息不匹配
    private String cause;
    private Date createdTime;
    private String transactionId;
    private int payWay;//0:微信 1：支付宝
}
