package com.rysh.module.pay.beans;

import lombok.Data;

import java.util.Date;

@Data
public class PayOrderError {
    private String id;
    private String ordersId;
    private String transactionId;
    private Date tradeTime;
    private int payWay;//0:微信 1.支付宝
}
