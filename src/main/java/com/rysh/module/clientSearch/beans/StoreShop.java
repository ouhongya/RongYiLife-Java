package com.rysh.module.clientSearch.beans;

import com.rysh.module.serverSystem.beans.StoreCommunity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@ApiModel("周边商铺对象")
@Data
public class StoreShop {
    @ApiModelProperty(name = "id",value = "商铺id")
    private String id;  //商铺id
    @ApiModelProperty(name = "name",value = "商铺名称")
    private String name; //商铺名称
    @ApiModelProperty(name = "contactNum",value = "商铺联系电话")
    private String contactNum;
    @ApiModelProperty(name = "address",value = "商铺地址")
    private String address; //商铺地址
    @ApiModelProperty(name = "cover",value = "封面图")
    private String cover;  //商铺封面图
    @ApiModelProperty(name = "images",value = "相册图")
    private List<String> images; //商铺相册图
    @ApiModelProperty(name = "freight",value = "运费")
    private BigDecimal freight; //运费
    @ApiModelProperty(name = "createdTimeStr",value = "商铺创建时间")
    private String createdTimeStr; //商铺创建时间
    @ApiModelProperty(name = "sales",value = "商铺销量")
    private Integer sales;  //商铺销量
    @ApiModelProperty(name = "mark",value = "商铺评分")
    private Double mark;  //商铺评分
    @ApiModelProperty(name = "storeCommunitys",value = "商铺绑定的小区信息")
    private List<StoreCommunity> storeCommunitys=new ArrayList<>();  //商铺绑定的小区信息

}
