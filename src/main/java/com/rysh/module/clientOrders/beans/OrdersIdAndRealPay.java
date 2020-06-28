package com.rysh.module.clientOrders.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrdersIdAndRealPay {
    private String orderId;
    private BigDecimal realPay;
}
