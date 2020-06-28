package com.rysh.module.serverOrders.beans;

import lombok.Data;

@Data
public class Delivers {
    private String splitId;   //子订单id
    private ShopAddressResult shopAddressResult;   //发货信息
}
