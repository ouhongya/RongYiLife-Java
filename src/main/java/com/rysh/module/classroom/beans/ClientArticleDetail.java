package com.rysh.module.classroom.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel("客户端文章详情")
public class ClientArticleDetail {
    @ApiModelProperty(name = "title",value = "文章标题",required = true)
    private String title;

    @ApiModelProperty(name = "contents",value = "文章内容部分",dataType = "List")
    private List<ArticleTxt> contents;

    @ApiModelProperty(name = "cover",value = "封面图片")
    private String cover;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(name = "createdTime",value = "文章创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "createdTimeMilli",value = "创建时间的毫秒值")
    private Long createdTimeMilli;

    @ApiModelProperty(name = "isLiked",value = "是否已经点赞")
    private Boolean isLiked = false;

    @ApiModelProperty(name = "likeNum",value = "文章点赞数")
    private Integer likeNum = 0;


}
