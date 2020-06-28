package com.rysh.module.serverOrders.beans;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalMoney {
    private BigDecimal goodsMoney;   //商品合计金额
    private BigDecimal freight;  //运费
    private BigDecimal scoreMoney;  //积分抵扣金额
    private BigDecimal realMoney;  //实付金额
}
