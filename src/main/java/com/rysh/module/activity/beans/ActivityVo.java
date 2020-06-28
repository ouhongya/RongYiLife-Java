package com.rysh.module.activity.beans;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
@ApiModel("活动对象")
@Data
public class ActivityVo {
    @ApiModelProperty(name = "id",value = "活动id")
    private String id;
    @ApiModelProperty(name = "name",value = "活动名称")
    private String name;
    private Date startTime;
    @ApiModelProperty(name = "startTimsMilli",value = "活动开始时间")
    private Long startTimeMilli;
    private Date endTime;
    @ApiModelProperty(name = "endTimeMilli",value = "活动结束时间")
    private Long endTimeMilli;
    @ApiModelProperty(name = "member",value = "活动总名额")
    private int member;
    @ApiModelProperty(name = "freeMember",value = "剩余名额")
    private int freeMember;
    @ApiModelProperty(name = "location",value = "活动地点")
    private String location;
    @ApiModelProperty(name = "publisher",value = "活动发起人")
    private String publisher;
    @ApiModelProperty(name = "publisherUnit",value = "发起单位")
    private String publisherUnit;//活动发起单位
    @ApiModelProperty(name = "contactNum",value = "活动联系电话")
    private String contactNum;//联系方式
    private Date applyStartTime;
    private Long applyStartTimeMilli;
    private Date applyEndTime;
    private Long applyEndTimeMilli;
    private List<ActivityImg> imgs;//活动图片
    @ApiModelProperty(name = "contents",value = "活动内容")
    private List<ActivityContent> contents;//活动内容
    @ApiModelProperty(name = "urls",value = "活动banner图")
    private List<String> urls;
    @ApiModelProperty(name = "cover",value = "活动封面图")
    private String cover;
    @ApiModelProperty(name = "areaId",value = "活动绑定的区域id")
    private String areaId;
    private int pass;
    private Date createdTime;
    @ApiModelProperty(name = "isSignUp",value = "当前用户是否能报名此活动  true 可以报名  false 不能报名")
    private Boolean isSignUp = true;
//    @ApiModelProperty(name = "isStartSign",value = "活动报名是否开始  true 已经开始   false 未开始")
//    private Boolean isStartSign = false;
//    @ApiModelProperty(name = "isEndSign",value = "活动报名是否结束   true 已经结束   false  未结束")
//    private Boolean isEndSign = false;
//    @ApiModelProperty(name = "isStart",value = "活动是否开始  true 活动已经开始    false 活动未开始")
//    private Boolean isStart = false;
//    @ApiModelProperty(name = "isEnd",value = "活动是否结束   true  活动已经结束   false活动未结束")
//    private Boolean isEnd = false;
    @ApiModelProperty(name = "isState",value = "能否报名")
    private Boolean isState = false;
}
