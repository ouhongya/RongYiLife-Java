package com.rysh.module.clientLoginRegister.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
@ApiModel("移动端user")
@Data
public class User {
    @ApiModelProperty(value = "用户id",name = "id")
    @Id
    private String id;    //用户id
    @ApiModelProperty(value = "用户登陆名   手机号",name = "loginName")
    private String loginName;  //用户登陆名   手机号
    @ApiModelProperty(value = "用户名称",name = "name")
    private String  name;   //用户名称
    @ApiModelProperty(value = "用户所绑定的小区id",name = "communityId")
    private String communityId;  //用户所绑定的小区id
    @ApiModelProperty(value = "用户所绑定的小区名称",name = "communityName")
    private String communityName;   //用户所绑定的小区名称
    @ApiModelProperty(value = "用户绑定的小区所属的区域",name = "areaName")
    private String areaName;
    @ApiModelProperty(value = "用户所属区域绑定的城市",name = "cityName")
    private String cityName;
    @ApiModelProperty(value = "用户头像url",name = "avatar")
    private String avatar;  //用户头像url
    @ApiModelProperty(value = "用户积分",name = "score",dataType = "Integer")
    private Integer score;   //用户积分
    @ApiModelProperty(value = "用户创建时间",name = "createdTime")
    private Date createdTime;  //用户创建时间
    @ApiModelProperty(value = "用户创建时间字符串",name = "createdTimeStr")
    private String createdTimeStr; //用户创建时间字符串
    @ApiModelProperty(value = "token字符串",name = "token")
    private String token;  //token字符串
    @ApiModelProperty(name = "discountRate",value = "积分折扣比例")
    private Integer discountRate;
    @ApiModelProperty(name = "minimums",value = "使用积分的最低消费限制")
    private Integer minimums;
}
