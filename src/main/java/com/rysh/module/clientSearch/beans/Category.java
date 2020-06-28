package com.rysh.module.clientSearch.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("商品分类对象")
public class Category {
    @ApiModelProperty(name = "id",value = "分类id")
    private String id;
    @ApiModelProperty(name = "categoryName",value = "分类名称")
    private String categoryName;
}
