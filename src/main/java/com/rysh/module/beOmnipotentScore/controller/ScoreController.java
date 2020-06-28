package com.rysh.module.beOmnipotentScore.controller;

import com.rysh.module.beOmnipotentScore.beans.ScoreDetail;
import com.rysh.module.beOmnipotentScore.service.ScoreService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(description = "积分来源查询")
@Log4j2
@RequestMapping(value = "/server/score")
@RestController
public class ScoreController {

    @Autowired
    private ScoreService scoreService;


    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @ApiOperation(value = "查询店铺积分来源",httpMethod = "GET")
    @ApiImplicitParam(name = "marketId",value = "店铺id")
    @GetMapping(value = "/findMarketScoreSource/{marketId}")
    public QueryResponseResult findMarketScoreSource(@PathVariable("marketId") String marketId){
        try {
            List<ScoreDetail> scoreDetails=scoreService.findMarketScoreSource(marketId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(scoreDetails);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询店铺积分来源异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
