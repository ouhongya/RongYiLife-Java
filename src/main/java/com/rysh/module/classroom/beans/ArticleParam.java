package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("后台添加时需要的参数")
public class ArticleParam {

    private String id;

    @ApiModelProperty(name = "title",value = "文章标题",required = true)
    private String title;

    @ApiModelProperty(name = "contents",value = "文章内容部分",dataType = "List",required = true)
    private List<ArticleTxt> contents;

    @ApiModelProperty(name = "author",value = "作者",required = true)
    private String author;

    @ApiModelProperty(name = "cover",value = "封面图片")
    private String cover;

    @ApiModelProperty(name = "status",value = "发布状态 0:未发布到客户端 1:已发布到客户端")
    private String status;

    @ApiModelProperty(name = "createdTime",value = "文章创建时间")
    private String createdTime;

}
