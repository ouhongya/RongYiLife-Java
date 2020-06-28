package com.rysh.module.statistics.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.UserDetail;
import com.rysh.module.statistics.service.StatisticsService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server/statistics")
public class UserStatisticsController {

    @Autowired
    private StatisticsService service;

    @ApiOperation(value = "统计每日用户注册数量",notes = "时间格式为例如【2019-10-01】")
    @GetMapping("/user/dur/{startTime}/{endTime}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime",value = "开始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间"),
    })
    public QueryResponseResult dailyUserRegistration(@PathVariable(name = "startTime") String startTime,@PathVariable(name = "endTime") String endTime){
        try {
            DayUserRig dayUserRig = service.getDailyUserRegistration(startTime,endTime);
            QueryResult<DayUserRig> result = new QueryResult<>();
            result.setData(dayUserRig);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("每日用户注册统计详情")
    @GetMapping("/user/durdetail")
    public QueryResponseResult dailyUserRegistrationDetail(ParamBean paramBean, String startTime, String endTime){
        try {
            PageInfo<UserDetail> users = service.getDailyUserRegistrationDetail(paramBean,startTime,endTime);
            QueryResult<UserDetail> result = new QueryResult<>();
            result.setData(users);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
