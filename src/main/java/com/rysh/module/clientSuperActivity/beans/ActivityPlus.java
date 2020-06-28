package com.rysh.module.clientSuperActivity.beans;

import com.rysh.module.activity.beans.ActivityContent;
import com.rysh.module.activity.beans.ActivityImg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ActivityPlus {
    @ApiModelProperty(name = "id",value = "活动id")
    private String id;
    @ApiModelProperty(name = "name",value = "活动名称")
    private String name;
    @ApiModelProperty(name = "startTimsMilli",value = "活动开始时间")
    private Long startTimeMilli;
    @ApiModelProperty(name = "endTimeMilli",value = "活动结束时间")
    private Long endTimeMilli;
    @ApiModelProperty(name = "member",value = "活动总名额")
    private int member;
    @ApiModelProperty(name = "freeMember",value = "剩余名额")
    private int freeMember;
    private String location;
    @ApiModelProperty(name = "publisher",value = "活动发起人")
    private String publisher;
    @ApiModelProperty(name = "publisherUnit",value = "发起单位")
    private String publisherUnit;//活动发起单位
    @ApiModelProperty(name = "contactNum",value = "活动联系电话")
    private String contactNum;//联系方式
    @ApiModelProperty(name = "applyStartTimeMilli",value = "活动报名开始时间")
    private Long applyStartTimeMilli;
    @ApiModelProperty(name = "applyEndTimeMilli",value = "活动报名结束时间")
    private Long applyEndTimeMilli;
    @ApiModelProperty(name = "contents",value = "活动内容")
    private List<String> contents = new ArrayList<>();//活动内容
    @ApiModelProperty(name = "urls",value = "活动banner图")
    private List<String> urls;
    @ApiModelProperty(name = "cover",value = "活动封面图")
    private String cover;
    @ApiModelProperty(name = "areaId",value = "活动绑定的区域id")
    private String areaId;
    @ApiModelProperty(name = "isSignUp",value = "当前用户是否能报名此活动  true 可以报名  false 不能报名")
    private Boolean isSignUp = true;
    @ApiModelProperty(name = "isState",value = "能否报名")
    private Boolean isState = false;
}
