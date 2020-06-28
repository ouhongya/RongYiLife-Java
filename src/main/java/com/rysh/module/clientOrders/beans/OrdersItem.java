package com.rysh.module.clientOrders.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
@ApiModel("订单商品明细对象")
@Data
public class OrdersItem {
    @ApiModelProperty(value = "订单明细id",name = "id")
    @Id
    private String id; //订单明细表id  主键
    @ApiModelProperty(value = "订单总表id",name = "ordersId")
    private String ordersId;  //订单表id  外键
    @ApiModelProperty(value = "商品id",name = "productItemId")
    private String productItemId; //商品id
    @ApiModelProperty(name = "productSpecId",value = "商品规格id")
    private String productSpecId;
    @ApiModelProperty(value = "商品单价",name = "price",dataType = "BigDecimal")
    private BigDecimal price;  //商品单价
    @ApiModelProperty(name = "categoryName",value = "商品分类名称")
    private String categoryName; //商品分类
    @ApiModelProperty(name = "productName",value = "商品名称")
    private String productName; //商品名称
    @ApiModelProperty(name = "unit",value = "商品规格")
    private String unit;  //商品规格
    @ApiModelProperty(name = "productUrl",value = "商品缩略图")
    private String productUrl;  //商品缩略图
    @ApiModelProperty(value = "商品数量",name = "quantity",dataType = "Integer")
    private Integer quantity;  //商品数量
    @ApiModelProperty(name = "goodsPrice",value = "商品单价*商品数量")
    private BigDecimal goodsPrice;
    @ApiModelProperty(value = "订单拆分表id",name = "ordersSplitId")
    private String ordersSplitId; //订单拆分表id  外键
    @ApiModelProperty(value = "订单明细生成时间",name = "createdTime")
    private Date createdTime;  //订单明细生成时间
    @ApiModelProperty(value = "订单明细生成时间字符串",name = "createdTimeStr")
    private String createdTimeStr;  //订单明细生成时间字符串
    @ApiModelProperty(value = "商品所属的购物车id",name = "shoppingCartId")
    private String shoppingCartId;
}
