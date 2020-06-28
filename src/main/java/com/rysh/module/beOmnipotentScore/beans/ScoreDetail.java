package com.rysh.module.beOmnipotentScore.beans;

import com.rysh.module.clientOrders.beans.OrdersItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("积分明细对象")
@Data
public class ScoreDetail {

    @ApiModelProperty(name = "ordersNum",value = "订单号")
    private String ordersNum;
    @ApiModelProperty(name = "marketScore",value = "店铺应得的积分")
    private Integer marketScore;
    @ApiModelProperty(name = "userScore",value = "用户应得积分")
    private Integer userScore;
    @ApiModelProperty(name = "ordersItems",value = "商品明细集合")
    private List<OrdersItem> ordersItems;
    @ApiModelProperty(name = "createdTimeStr",value = "积分消费时间")
    private String createdTimeStr;
}
