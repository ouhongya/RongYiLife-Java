package com.rysh.module.clientOrders.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("商品id，商品类型对象")
@Data
public class FalseOrders {
    @ApiModelProperty(name = "isInsider",value = "商品类型  1商铺  2农场  3农庄 4自营")
    private Integer isInsider;
    @ApiModelProperty(name = "goodsSpecId",value = "商品规格id")
    private String goodsSpecId;
    @ApiModelProperty(name = "quantity",value = "商品数量")
    private Integer quantity;
}
