package com.rysh.module.clientLeSeClass.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("垃圾对象")
@Data
public class SuperLeSe {
    @ApiModelProperty(name = "id",value = "垃圾id")
    private String id;
    @ApiModelProperty(name = "name",value = "垃圾名称")
    private String name;
    @ApiModelProperty(name = "categoryName",value = "垃圾所属分类名称")
    private String categoryName;
}
