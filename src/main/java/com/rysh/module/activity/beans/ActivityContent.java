package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/*
* 活动内容
* */
@ApiModel("活动内容")
@Data
public class ActivityContent {
    private String id;
    @ApiModelProperty(name = "content",value = "活动描述内容")
    private String content;

    private int queue;
    private String activityId;
}
