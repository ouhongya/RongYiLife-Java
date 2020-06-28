package com.rysh.module.clientOrders.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApiModel(value = "订单对象")
@Data
public class Orders {
    @Id
    @ApiModelProperty(value = "主键id",name = "id")
    private String id; //订单id  主键
    @ApiModelProperty(value = "用户id",name = "userId")
    private String userId; //用户id
    @ApiModelProperty(value = "实际支付金额",name = "realPay",dataType ="BigDecimal")
    private BigDecimal realPay;  //实付金额
    @ApiModelProperty(value = "订单编号 自动生成",name = "ordersNum")
    private String ordersNum;  //订单编号，自动生成
    @ApiModelProperty(value = "交易编号 微信或者支付宝返回",name = "tradeNum")
    private String tradeNum;  //交易编号  微信或者支付宝返回
    @ApiModelProperty(value = "支付方式，0为微信，1为支付宝",name = "payWay",dataType = "Integer")
    private Integer payWay;  //支付方式，0为微信，1为支付宝
    @ApiModelProperty(value = "支付时间  ",name = "payTime")
    private Date payTime;  //支付时间
    @ApiModelProperty(value = "下单时间",name = "createdTime")
    private Date createdTime;  //订单下单时间
    @ApiModelProperty(value = "下单时间字符串",name = "createdTimeStr")
    private String createdTimeStr;  //订单下单时间字符串
    @ApiModelProperty(name = "lastedUpdateTime",value = "订单最后更新时间")
    private Date lastedUpdateTime;
    @ApiModelProperty(value = "订单状态。1-未支付 2-已支付 3-取消",name = "state")
    private Integer state;  //订单状态。1-未支付 2-已支付 3-取消
    @ApiModelProperty(value = "子订单集合",name = "ordersSplits")
    private List<OrdersSplit> ordersSplits;   //子订单集合
    @ApiModelProperty(value = "收货人姓名",name = "name")
    private String name;  //收货人名称
    @ApiModelProperty(value = "收货人电话",name = "phone")
    private String phone; //收货人电话
    @ApiModelProperty(value = "收货地址",name = "address")
    private String address;  //收货地址
    @ApiModelProperty(value = "邮政编码",name = "zipCode")
    private String zipCode;  //邮政编码
    @ApiModelProperty(name = "ExpirationTime",value = "未支付订单有效时间")
    private Long ExpirationTime;
    @ApiModelProperty(value = "token字符串",name = "token")
    private String token;   //token字符串
}
