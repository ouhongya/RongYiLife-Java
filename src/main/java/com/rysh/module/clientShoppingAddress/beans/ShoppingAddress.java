package com.rysh.module.clientShoppingAddress.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel("收货地址对象")
@Data
public class ShoppingAddress {
    @ApiModelProperty(name = "id",value = "收获地址id")
    private String id;  //收获地址id
    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId;  //用户id
    @ApiModelProperty(name = "name",value = "收货人姓名")
    private String name;  //收货人姓名
    @ApiModelProperty(name = "address",value = "收获地址")
    private String address;  //收获地址
    @ApiModelProperty(name = "phone",value = "收货人电话")
    private String phone;  //收货人电话
    @ApiModelProperty(name = "zipCode",value = "邮政编码")
    private String zipCode;  //邮编
    @ApiModelProperty(name = "createdTime",value = "收货地址创建时间")
    private Date  createdTime;  //收货地址创建时间
    @ApiModelProperty(name = "createdTimeStr",value = "收货地址创建时间字符串")
    private String  createdTimeStr;  //收货地址创建时间字符串
    @ApiModelProperty(name = "token",value = "token字符串")
    private String token;  //token字符串
    @ApiModelProperty(name = "state",value = "是否是默认收货地址  1默认  0不默认")
    private Integer state;
}
