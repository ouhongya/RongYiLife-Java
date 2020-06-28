package com.rysh.module.productCustomize.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductCustomizeVo {

    private String id;

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "cover",value = "封面")
    private String cover;

    @ApiModelProperty(name = "defaultSort",value = "排序字段(number)")
    private int defaultSort;

    @ApiModelProperty(name = "contents",value = "内容")
    private List<ProductCustomizeContent> contents;

    @ApiModelProperty(name = "productCustomizedCategoryId",value = "分类Id")
    private String productCustomizedCategoryId;
}
