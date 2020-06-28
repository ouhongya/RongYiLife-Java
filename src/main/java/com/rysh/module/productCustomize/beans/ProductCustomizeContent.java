package com.rysh.module.productCustomize.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
* 社区定制内容
* */
@Data
public class ProductCustomizeContent {
    private String id;

    @ApiModelProperty(name = "content",value = "内容")
    private String content;

    @ApiModelProperty(name = "type",value = "类型 0:图片 1：文字")
    private int type;

    @ApiModelProperty(name = "queue",value = "排序顺序")
    private int queue;

    private String productCustomizedId;
}
