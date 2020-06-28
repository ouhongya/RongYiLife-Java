package com.rysh.module.statistics.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("订单下被统计的商品")
@Data
public class OrdersItemSS {
    @ApiModelProperty(name = "itemName",value = "商品名称")
    private String itemName;
    @ApiModelProperty(name = "itemPrice",value = "商品单价")
    private BigDecimal itemPrice;
    @ApiModelProperty(name = "quantity",value = "商品数量")
    private Integer quantity;
    @ApiModelProperty(name = "img",value = "商品封面图")
    private String img;
}
