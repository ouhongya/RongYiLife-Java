package com.rysh.module.serverOrders.beans;

import com.rysh.module.clientOrders.beans.OrdersItem;
import com.rysh.module.clientShoppingAddress.beans.ShoppingAddress;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ImIronMan {
    private Integer state;  //订单状态码   1已支付 2已发货 3交易成功
    private SendUser sendUser;  //发货人信息
    private List<OrdersItem> ordersItems=new ArrayList<>();  //商品信息
    private ShoppingAddress shoppingAddress;  //收货人信息
    private TotalMoney totalMoney;  //金额信息
}
