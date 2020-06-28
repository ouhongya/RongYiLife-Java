package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ApiModel("")
@Data
public class ProductItem {
    @ApiModelProperty(name = "itemId",value = "商品id")
    private String itemId;  //商品id
    @ApiModelProperty(name = "name",value = "商品名称")
    private String name;  //商品名称
    @ApiModelProperty(name = "categoryId",value = "商品分类id")
    private String categoryId; //商品分类Id
    @ApiModelProperty(name = "categoryName",value = "商品分类名称")
    private String categoryName;  //商品分类名称
    @ApiModelProperty(name = "createdTimeStr",value = "商品创建时间")
    private String createdTimeStr;  //商品创建时间
    @ApiModelProperty(name = "description",value = "商品描述")
    private String description;  //商品描述
    @ApiModelProperty(name = "price",value = "商品单价")
    private BigDecimal price;  //商品单价
    @ApiModelProperty(name = "unit",value = "商品规格")
    private String unit;  //商品规格
    @ApiModelProperty(name = "productSpecs",value = "商品规格集合")
    private List<ProductSpec> units = new ArrayList<>();
    @ApiModelProperty(name = "detailUrl",value = "商品详情url")
    private String detailUrl;  //商品详情图片url
    @ApiModelProperty(name = "coverUrl",value = "商品封面图")
    private String coverUrl;
    @ApiModelProperty(name = "urls",value = "商品banner图")
    private List<String> urls;  //商品banner图
    @ApiModelProperty(name = "address",value = "自营商城发货地址")
    private String address;
    @ApiModelProperty(name = "sales",value = "商品销量")
    private Integer sales;  //商品销量
    @ApiModelProperty(name = "freight",value = "运费")
    private BigDecimal freight;  //运费
    @ApiModelProperty(name = "belongType",value = "商品类型")
    private Integer belongType;
    @ApiModelProperty(name = "marketId",value = "店铺id")
    private String marketId;
    @ApiModelProperty(name = "marketName",value = "店铺名称")
    private String marketName;
    @ApiModelProperty(name = "marketCover",value = "店铺头像")
    private String marketCover;
}
