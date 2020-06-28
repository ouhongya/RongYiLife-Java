package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@ApiModel
@Data
public class SuperProductItem {
    @ApiModelProperty(name = "categories",value = "商品分类集合")
    List<Category> categories;  //商品分类集合
    @ApiModelProperty(name = "productItems",value = "商品集合")
    List<ProductItem> productItems; //商品集合
}
