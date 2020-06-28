package com.rysh.module.serverOrders.beans;

import lombok.Data;

@Data
public class SendUser {

    private String createdTimeStr;  //订单创建时间

    private String orderNum;  //订单编号

    private String zipCode;  //邮编

    private String courierNum;  //物流单号

    private String name;  //发货人名字

    private Integer payWay;  //支付方式  0 微信  1支付宝

    private String courier;  //快递方式
}
