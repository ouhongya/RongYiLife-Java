package com.rysh.module.serverSystem.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商铺所绑定的小区对象")
public class StoreCommunity {
    @ApiModelProperty(name = "id",value = "小区id")
    private String communityId;
    @ApiModelProperty(name = "name",value = "小区名称")
    private String communityName;
    @ApiModelProperty(name = "areaId",value = "区域id")
    private String areaId;  //区域id
    @ApiModelProperty(name = "cityId",value = "城市id")
    private String cityId;  //城市id
}
