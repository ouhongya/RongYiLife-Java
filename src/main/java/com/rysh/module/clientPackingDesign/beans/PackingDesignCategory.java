package com.rysh.module.clientPackingDesign.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("社群定制分类")
@Data
public class PackingDesignCategory {
    @ApiModelProperty(name = "id",value = "分类id")
    private String id;
    @ApiModelProperty(name = "name",value = "分类名称")
    private String name;
}
