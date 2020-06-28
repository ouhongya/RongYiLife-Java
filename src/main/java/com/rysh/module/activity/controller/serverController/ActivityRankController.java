package com.rysh.module.activity.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.activity.beans.ClientRankResponse;
import com.rysh.module.activity.beans.Rank;
import com.rysh.module.activity.beans.RankItem;
import com.rysh.module.activity.beans.RankParam;
import com.rysh.module.activity.service.RankService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/server/activity/rank")
@Api(description = "活动排名")
public class ActivityRankController implements ServerUserControllerApi {

    @Autowired
    private RankService service;

    @ApiOperation("新增加排名")
    @PostMapping("/add")
    public QueryResponseResult addRank(@RequestBody RankParam rankParam){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            service.addRank(rankParam,login);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
           log.error("添加排名失败"+e);
           return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有的排行纪录")
    @GetMapping("/all")
    public QueryResponseResult allRank(ParamBean paramBean){
        PageInfo<Rank> rankList = null;
        try {
            rankList = service.allRank(paramBean);
            QueryResult<Rank> result = new QueryResult<>();
            result.setData(rankList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询所有的排行纪录出错啦@QAQ@"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("排行榜详情 【传入排行榜Id】")
    @GetMapping("/{id}")
    public QueryResponseResult rankItem(@PathVariable String id){
        try {
            ClientRankResponse resultList = service.getRankItemByRankId(id);
            QueryResult<RankItem> result = new QueryResult<>();
            result.setData(resultList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询排行榜详情时出错"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


}
