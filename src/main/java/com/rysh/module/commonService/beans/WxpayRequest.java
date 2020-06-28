package com.rysh.module.commonService.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WxpayRequest {
    @ApiModelProperty(name = "amount",value = "商品总价格")
    private String amount;

    @ApiModelProperty(name = "orderId",value = "订单编号")
    private String orderId;
}
