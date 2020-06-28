package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("文章内容")
public class ArticleContent {

    @ApiModelProperty(name = "id",value = "主键")
    private String id;

    @ApiModelProperty(name = "content",value = "内容主题内容(可能是文字 也可能是图片URL)",required = true)
    private String content;

    @ApiModelProperty(name = "type",value = "内容类型 0:图片 1:文字",required = true)
    private int type;

    @ApiModelProperty(name = "queue",value = "展示顺序",required = true)
    private int queue;

    @ApiModelProperty(name = "articleId",value = "关联的文章主键")
    private String articleId;
}
