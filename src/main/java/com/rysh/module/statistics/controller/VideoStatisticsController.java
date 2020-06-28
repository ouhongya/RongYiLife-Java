package com.rysh.module.statistics.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.VideoDetail;
import com.rysh.module.statistics.service.VideoStatisticsService;
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
@Api(description = "视频统计")
public class VideoStatisticsController {

    @Autowired
    private VideoStatisticsService service;

    @ApiOperation("视频统计")
    @GetMapping("/video/dps")
    public QueryResponseResult videoStatistics(String startTime,String endTime){
        try {
            DayUserRig dayUserRig = service.getVideoStatistics(startTime,endTime);
            QueryResult<DayUserRig> result = new QueryResult<>();
            result.setData(dayUserRig);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("视频统计详情")
    @GetMapping("/video/detail")
    public QueryResponseResult videoStatisticsDetail(ParamBean paramBean,String startTime,String endTime){
        try {
            PageInfo<VideoDetail> activities = service.getVideoStatisticsDetail(paramBean,startTime,endTime);
            QueryResult<VideoDetail> result = new QueryResult<>();
            result.setData(activities);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }




}
