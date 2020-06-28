package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/*
*客户端文章类
* */
@Data
@ApiModel("客户端文章主体")
public class ClientArticle {
    @ApiModelProperty(name = "id",value = "主键")
    private String id;

    @ApiModelProperty(name = "title",value = "文章标题")
    private String title;

    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty(name = "createdTime",value = "文章创建时间",dataType = "Date")
    private Date createdTime;

    @ApiModelProperty(name = "cover",value = "封面图片")
    private String cover;

    @ApiModelProperty(name = "intro",value = "文章简介")
    private String intro;

    @ApiModelProperty(name = "createdTimeMilli",value = "创建时间的毫秒值")
    private Long createdTimeMilli;

    @ApiModelProperty(name = "isLiked",value = "是否已经点赞")
    private Boolean isLiked = false;

    @ApiModelProperty(name = "likeNum",value = "文章点赞数")
    private Integer likeNum = 0;

}
