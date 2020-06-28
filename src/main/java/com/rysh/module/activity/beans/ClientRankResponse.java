package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("家庭之星排行榜客户端响应数据结构")
public class ClientRankResponse {

    @ApiModelProperty(name = "detail",value = "用户详细信息")
    List<ClientRankUserInfo> detail;

    @ApiModelProperty(name = "banner",value = "首页图片")
    private String banner;

    @ApiModelProperty(name = "introduction",value = "简介")
    private String introduction;
}
