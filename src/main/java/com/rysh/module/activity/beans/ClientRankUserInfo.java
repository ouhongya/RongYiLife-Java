package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家庭之星排行榜用户详细信息")
public class ClientRankUserInfo {
    @ApiModelProperty(name = "no",value = "用户排名")
    private int no;

    @ApiModelProperty(name = "name",value = "用户姓名")
    private String name;

    @ApiModelProperty(name = "avatar",value = "用户头像")
    private String avatar;
}

