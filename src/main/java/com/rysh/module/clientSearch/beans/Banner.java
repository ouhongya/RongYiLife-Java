package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("自营商城banner图对象")
@Data
public class Banner {
    @ApiModelProperty(name = "id",value = "图片绑定的店铺或者商品的id")
    private String mid;
    @ApiModelProperty(name = "id_type",value = "id类型  0店铺  1商品")
    private Integer id_type;
    @ApiModelProperty(name = "img",value = "图片url")
    private String img;
}
