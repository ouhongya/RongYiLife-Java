package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("家庭之星排行榜客户端响应")
@Data
public class ClientRank {

    @ApiModelProperty(name = "no",value = "用户排名")
    private int no;

    @ApiModelProperty(name = "name",value = "用户姓名")
    private String name;

    @ApiModelProperty(name = "avatar",value = "用户头像")
    private String avatar;

    @ApiModelProperty(name = "banner",value = "首页图片")
    private String banner;

    @ApiModelProperty(name = "introduction",value = "简介")
    private String introduction;
}
