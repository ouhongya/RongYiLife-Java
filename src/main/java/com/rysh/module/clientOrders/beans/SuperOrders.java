package com.rysh.module.clientOrders.beans;

import com.rysh.module.clientShoppingCart.beans.ShoppingCart;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel("前端需要传递给我的订单对象")
@Data
public class SuperOrders {
    @ApiModelProperty(name = "allPrice",value = "购买商品实付价格")
   private BigDecimal allPrice; //购买商品实付价格
    @ApiModelProperty(name = "shoppingCarts",value = "购物车商品集合")
    private List<ShoppingCart> shoppingCarts;
    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;
    @ApiModelProperty(name = "shopAddressName",value = "收货人名称")
    private String shopAddressName;
    @ApiModelProperty(name = "shopAddress",value = "收货地址")
    private String shopAddress;
    @ApiModelProperty(name = "shoppAddressPhone",value = "收货人电话")
    private String shoppAddressPhone;
    @ApiModelProperty(name = "shopAddressZipCode",value = "邮政编码")
    private String shopAddressZipCode;
}
