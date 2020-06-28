package com.rysh.module.clientOrders.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@ApiModel("子订单对象")
@Data
public class OrdersSplit {
    @ApiModelProperty(value = "子订单id",name = "id")
    @Id
    private String id;  //子订单id
    @ApiModelProperty(value = "总订单id ",name = "ordersId")
    private String ordersId;   //总订单id
    @ApiModelProperty(value = "订单编号",name = "ordersNum")
    private String ordersNum;  //订单号
    @ApiModelProperty(value = "子订单状态 1-已支付 2-发货 3-交易完成",name = "state")
    private Integer state;   //订单状态 1-已支付 2-发货 3-交易完成
    @ApiModelProperty(value = "运费",name = "freight")
    private BigDecimal freight;   //运费
    @ApiModelProperty(value = "使用了多少积分",name = "usedSorce")
    private Integer usedSorce;   //使用了多少积分
    @ApiModelProperty(value = "积分兑换比例 例：1RMB=10积分  discountRate=10",name = "discountRate")
    private Integer discountRate;  //积分兑换比例 例：1RMB=10积分  discountRate=10
    @ApiModelProperty(value = "用户消费比例 例：消费1RMB 返1积分  consumeRate=1",name = "consumeRate")
    private Integer consumeRate;
    @ApiModelProperty(value = "子订单的总价格",name = "realPlay")
    private BigDecimal realPlay;  //子订单的总价格
    @ApiModelProperty(value = "属于哪种类型的订单，1-商铺 2-农场 3-农庄 4-自营",name = "belongType")
    private Integer belongType;  //属于哪种类型的订单，1-商铺 2-农场 3-农庄 4-自营
    @ApiModelProperty(value = "订单拆分时间",name = "createdTime")
    private Date createdTime;   //订单拆分时间
    @ApiModelProperty(value = "订单拆分时间字符串",name = "createdTimeStr")
    private String createdTimeStr;   //订单拆分时间字符串
    @ApiModelProperty(value = "子订单下的商品集合",name = "ordersItems")
    private List<OrdersItem> ordersItems;  //子订单下的商品集合
    @ApiModelProperty(value = "该子订单所属的店铺id",name = "marketId")
    private String marketId;  //该子订单所属的店铺id
    @ApiModelProperty(value = "该子订单所属的店铺名称",name = "marketName")
    private String marketName; //该子订单所属的店铺名称
    @ApiModelProperty(name = "marketUrl",value = "店铺头像")
    private String marketUrl;
    @ApiModelProperty(value = "物流编码",name = "courierNum")
    private String courierNum;   //物流编码
    @ApiModelProperty(value = "快递方式",name = "courier")
    private String courier;  //快递方式
    @ApiModelProperty(name = "sellerMessage",value = "卖家留言")
    private String sellerMessage;
    @ApiModelProperty(name = "markState",value = "是否评价 -1 未评价  1评价1分  2评价2分 ---")
    private Integer markState = -1;
    @ApiModelProperty(name = "remindState",value = "是否可以提醒发货  true 可以提醒   false 不能提醒")
    private Boolean remindState;
    @ApiModelProperty(name = "payTime",value = "支付时间")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:ss:mm")
    private Date payTime;

}
