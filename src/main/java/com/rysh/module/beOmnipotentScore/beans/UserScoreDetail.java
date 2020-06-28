package com.rysh.module.beOmnipotentScore.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("用户积分对象")
@Data
public class UserScoreDetail {
    @ApiModelProperty(name = "marketUrl",value = "店铺头像")
    private String marketUrl;
    @ApiModelProperty(name = "marketName",value = "店铺名称")
    private String marketName;
    @ApiModelProperty(name = "score",value = "积分情况  负数为消费积分   正数为获得积分")
    private Integer score;
    @ApiModelProperty(name = "createTimeMills",value = "获得或者消费积分时间")
    private Long createTimeMills;
}
