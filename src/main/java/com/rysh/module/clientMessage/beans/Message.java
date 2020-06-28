package com.rysh.module.clientMessage.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel("消息对象")
@Data
public class Message {
    @ApiModelProperty(name = "id",value = "id主键")
    private String id;  //id主键
    @ApiModelProperty(name = "userId",value = "用户id")
    private String userId; //用户id
    @ApiModelProperty(name = "content",value = "消息体")
    private String content;  //消息体
    @ApiModelProperty(name = "createdTime",value = "消息创建时间")
    private Date createdTime;  //消息创建时间
    @ApiModelProperty(name = "createdTimeStr",value = "消息创建时间字符串")
    private String createdTimeStr;  //消息创建时间字符串
    @ApiModelProperty(name = "createTimeMills",value = "时间毫秒值")
    private Long createTimeMills;
    @ApiModelProperty(name = "isRead",value = "1 已读  0未读")
    private Integer isRead;  //1 已读  0未读
}
