package com.rysh.module.productCustomize.beans;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
/*
* 社群定制响应类
* */
public class ProductCustomizeRo {

    private String id;

    @ApiModelProperty(name = "title",value = "标题")
    private String title;

    @ApiModelProperty(name = "cover",value = "封面")
    private String cover;

    @ApiModelProperty(name = "defaultSort",value = "排序字段(number)")
    private int defaultSort;

    @ApiModelProperty(name = "contents",value = "内容")
    private List<ProductCustomizeContent> contents;

    @ApiModelProperty(name = "productCustomizedCategoryName",value = "分类名")
    private ProductCustomizeCategory productCustomizedCategory;

    @ApiModelProperty(name = "createdTime",value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "pass",value = "0:未审核 1：审核通过 -1:审核未通过")
    private int pass;

    @ApiModelProperty(name = "status",value = "0:未发布 1：已发布")
    private int status;

    @ApiModelProperty(name = "count",value = "留言人数")
    private Integer count;

    private String passComment;
}
