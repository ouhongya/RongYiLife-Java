package com.rysh.module.commonService.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@ApiModel("店铺对象")
@Data
public class ShopBean {
    @ApiModelProperty(name = "id",value = "店铺id")
    private String id;
    @ApiModelProperty(name = "name",value = "店铺名称")
    private String name;
    @ApiModelProperty(name = "address",value = "店铺地址")
    private String address;
    @ApiModelProperty(name = "score",value = "没用 不用管")
    private Integer score;
    @ApiModelProperty(name = "createdTime",value = "店铺创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createdTime;
    private int status;
    private Date lastedUpdateTime;
    @ApiModelProperty(name = "sales",value = "销量")
    private int sales;  //销量
    @ApiModelProperty(name = "intro",value = "店铺描述")
    private String intro;
    @ApiModelProperty(name = "contactNum",value = "联系电话")
    private String contactNum;
    @ApiModelProperty(name = "mark",value = "评分")
    private double mark;     //评分
    @ApiModelProperty(name = "cover",value = "封面图")
    private String cover;    //封面图
    @ApiModelProperty(name = "images",value = "相册图")
    private List<String> images;     //相册图
    @ApiModelProperty(name = "freight",value = "运费")
    private BigDecimal freight;
}
