package com.rysh.module.classroom.beans;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Video {

    @ApiModelProperty(name = "id",value = "主键")
    private String id;

    @ApiModelProperty(name = "title",value = "视频标题")
    private String title;

    @ApiModelProperty(name = "intros",value = "视频简介")
    private String intros;

    @ApiModelProperty(name = "url",value = "视频url")
    private String url;

    @ApiModelProperty(name = "pass",value = "审核状态")
    private int pass;

    @ApiModelProperty(name = "passOperator",value = "审核人")
    private String passOperator;

    @ApiModelProperty(name = "passComment",value = "审核备注")
    private String passComment;

    @ApiModelProperty(name = "passTime",value = "审核时间")
    private Date passTime;

    @ApiModelProperty(name = "createdTime",value = "视频创建时间")
    private Date createdTime;

    @ApiModelProperty(name = "status",value = "数据有效位")
    private int status;

    @ApiModelProperty(name = "defaultSort",value = "默认排序大小")
    private int defaultSort;

    @ApiModelProperty(name = "operator",value = "操作人")
    private String operator;

    @ApiModelProperty(name = "likedNum",value = "点赞数")
    private Integer likedNum = 0;

    @ApiModelProperty(name = "isUp",value = "当前视频是否已经被当前用户点赞   true赞了  false 没赞")
    private Boolean isUp = false;

    @ApiModelProperty(name = "createTimeMills",value = "时间毫秒值")
    private Long createTimeMills;
}
