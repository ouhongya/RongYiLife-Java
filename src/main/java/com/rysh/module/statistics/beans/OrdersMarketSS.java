package com.rysh.module.statistics.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("被统计的店铺")
@Data
public class OrdersMarketSS {
    @ApiModelProperty(name = "id",value = "店铺id")
    private String id;
    @ApiModelProperty(name = "marketName",value = "店铺名称")
    private String marketName;
    @ApiModelProperty(name = "marketOrdersCount",value = "店铺订单数量")
    private Integer marketOrdersCount;
    @ApiModelProperty(name = "marketPrice",value = "店铺总金额")
    private BigDecimal marketPrice = new BigDecimal("0");
    @ApiModelProperty(name = "score",value = "店铺积分")
    private Integer score;
}
