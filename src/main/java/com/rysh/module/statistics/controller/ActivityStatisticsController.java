package com.rysh.module.statistics.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.JoinedUser;
import com.rysh.module.statistics.service.ActivityStatisticsService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server/statistics")
@Api(description = "活动统计")
public class ActivityStatisticsController {

    @Autowired
    private ActivityStatisticsService service;

    @ApiOperation("活动统计")
    @GetMapping("/activity/das")
    public QueryResponseResult activityStatistics(String startTime,String endTime){
        try {
            DayUserRig dayUserRig = service.getActivityStatistics(startTime,endTime);
            QueryResult<DayUserRig> result = new QueryResult<>();
            result.setData(dayUserRig);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "活动统计详情",notes = "排序字段：start_time, 顺序： asc")
    @GetMapping("/activity/dasdetail")
    public QueryResponseResult activityStatisticsDetail(ParamBean paramBean,String startTime,String endTime){
        try {
            PageInfo<ActivityDetail> activities = service.getActivityStatisticsDetail(paramBean,startTime,endTime);
            QueryResult<ActivityDetail> result = new QueryResult<>();
            result.setData(activities);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "查看参加活动的用户",notes = "请仔细查看传参方式！！！")
    @GetMapping("/activity/joinuser")
    public QueryResponseResult getJoinUserById(String id,ParamBean paramBean){
        try {
            PageInfo<JoinedUser> joins = service.getActivityJoinUser(id,paramBean);
            QueryResult<JoinedUser> result = new QueryResult<>();
            result.setData(joins);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

}
