package com.rysh.module.statistics.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("店铺下被统计的订单")
@Data
public class OrdersOrdersSplitSS {
    @ApiModelProperty(name = "id",value = "子订单id")
    private String id;
    @ApiModelProperty(name = "orderNum",value = "订单号")
    private String orderNum;
    @ApiModelProperty(name = "ordersPrice",value = "订单总金额")
    private BigDecimal ordersPrice;
    @ApiModelProperty(name = "itemCount",value = "订单下的商品数量")
    private Integer itemCount;
    @ApiModelProperty(name = "freight",value = "运费")
    private BigDecimal freight;
    @ApiModelProperty(name = "useScore",value = "使用的积分")
    private Integer useScore;
    @ApiModelProperty(name = "payTime",value = "订单支付时间")
    private String payTime;
}
