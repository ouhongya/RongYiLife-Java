package com.rysh.module.clientShoppingCart.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
@ApiModel("购物车对象")
@Data
public class ShoppingCart {
    @ApiModelProperty(name = "id",value = "购物车Id")
    @Id
    private String id;    //购物车Id
    @ApiModelProperty(name = "userId",value = "移动端的userId")
    private String userId;    //移动端的userId
    @ApiModelProperty(name = "goodsId",value = "商品id")
    private String goodsId;   //商品id
    @ApiModelProperty(name = "goodsName",value = "商品名称")
    private String goodsName; //商品名称
    @ApiModelProperty(name = "goodsNum",value = "商品数量")
    private Integer goodsNum;  //商品数量
    @ApiModelProperty(name = "goodsPrice",value = "商品单价")
    private BigDecimal goodsPrice;  //商品单价
    @ApiModelProperty(name = "totalPrice",value = "商品单价*商品数量")
    private BigDecimal totalPrice;   //商品单价  *  商品数量
    @ApiModelProperty(name = "unitId",value = "商铺规格id")
    private String unitId;
    @ApiModelProperty(name = "goodsUnit",value = "商品规格")
    private String goodsUnit; //商品规格
    @ApiModelProperty(name = "goodsImgUrl",value = "商品图片")
    private String goodsImgUrl;  //商品图片
    @ApiModelProperty(name = "freight",value = "运费")
    private BigDecimal freight;
    @ApiModelProperty(name = "isInsider",value = "第三方平台  1=商铺   2=农场  3=农庄  4=自营商城")
    private Integer isInsider;  //第三方平台  1=商铺   2=农场  3=农庄  4=自营商城
    @ApiModelProperty(name = "marketId",value = "商铺来自哪家店铺")
    private String marketId;   //商铺来自哪家店铺
    @ApiModelProperty(name = "marketName",value = "店铺名称")
    private String marketName; //店铺名称
    @ApiModelProperty(name = "marketUrl",value = "店铺图片")
    private String marketUrl;
    @ApiModelProperty(name = "createdTime",value = "购物车添加时间")
    private Date  createdTime;   //购物车添加时间
    @ApiModelProperty(name = "createdTimeStr",value = "购物车添加时间字符串")
    private String  createdTimeStr;   //购物车添加时间字符串
    @ApiModelProperty(name = "token",value = "token字符串")
    private String token;
}
