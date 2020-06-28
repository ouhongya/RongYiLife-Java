package com.rysh.module.clientMessage.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("消息+数量组合对象")
@Data
public class SuperMessage {
    @ApiModelProperty(name = "mesCount",value = "消息数量")
    private Integer mesCount; //消息数量
    @ApiModelProperty(name = "message",value = "消息对象集合")
    private List<Message> messages;  //消息对象集合
}
