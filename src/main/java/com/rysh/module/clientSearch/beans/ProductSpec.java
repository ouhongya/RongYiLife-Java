package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel("商品规格对象")
@Data
public class ProductSpec {
    @ApiModelProperty(name = "id",value = "规格id")
    private String id;
    @ApiModelProperty(name = "name",value = "规格名称")
    private String name;
    @ApiModelProperty(name = "price",value = "商品单价")
    private BigDecimal price;
}
