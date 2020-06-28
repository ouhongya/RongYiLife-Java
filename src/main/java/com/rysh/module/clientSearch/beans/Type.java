package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("自营商城分类对象")
@Data
public class Type {
    @ApiModelProperty(name = "id",value = "自营商城分类id")
    private String id;
    @ApiModelProperty(name = "name",value = "自营商城分类名称")
    private String name;
}
