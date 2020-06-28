package com.rysh.module.statistics.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ArticleDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.service.ArticleStatisticsService;
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
@Api(description = "文章统计")
public class ArticleStatisticsController {

    @Autowired
    private ArticleStatisticsService service;

    @ApiOperation("文章统计")
    @GetMapping("/article/dps")
    public QueryResponseResult articleStatistics(String startTime,String endTime){
        try {
            DayUserRig dayUserRig = service.articleStatistics(startTime,endTime);
            QueryResult<DayUserRig> result = new QueryResult<>();
            result.setData(dayUserRig);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "文章详情")
    @GetMapping("/article/detail")
    public QueryResponseResult articleDetail(ParamBean paramBean, String startTime, String endTime){
        try {
            PageInfo<ArticleDetail> activities = service.getArticleStatisticsDetail(paramBean,startTime,endTime);
            QueryResult<ArticleDetail> result = new QueryResult<>();
            result.setData(activities);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }

    }


}
