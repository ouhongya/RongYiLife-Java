package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("文章主体")
public class Article {

    @ApiModelProperty(name = "id",value = "主键")
    private String id;

    @ApiModelProperty(name = "title",value = "文章标题",required = true)
    private String title;

    @ApiModelProperty(name = "author",value = "文章作者")
    private String author;

    @ApiModelProperty(name = "createdTime",value = "文章创建时间",dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(name = "status",value = "数据有效位")
    private int status;

    @ApiModelProperty(name = "operator",value = "操作人")
    private String operator;

    @ApiModelProperty(name = "cover",value = "封面图片")
    private String cover;

    @ApiModelProperty(name = "defaultSort",value = "排序大小")
    private Integer defaultSort;

    @ApiModelProperty(name = "liked",value = "点赞数")
    private Integer liked;


}
