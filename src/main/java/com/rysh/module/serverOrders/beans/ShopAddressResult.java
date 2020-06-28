package com.rysh.module.serverOrders.beans;

import lombok.Data;

@Data
public class ShopAddressResult {
    private String ordersNum;  //订单号
    private String name;   //收货人姓名
    private String phone;  //收货人电话
    private String address;  //收货地址
    private String courier;  //快递方式
    private String courierNum;   //物流号
    private String zipCode;  //邮政编码
}
