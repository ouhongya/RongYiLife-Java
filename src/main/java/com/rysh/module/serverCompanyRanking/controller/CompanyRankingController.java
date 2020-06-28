package com.rysh.module.serverCompanyRanking.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankS;
import com.rysh.module.serverCompanyRanking.service.CompanyRankingService;
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

@Api(description = "后台公益之星")
@Log4j2
@RestController
@RequestMapping(value = "/server/participate")
public class CompanyRankingController {

    @Autowired
    private CompanyRankingService companyRankingService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "查询所有排行")
    @GetMapping(value = "/findAllCompanyRanking")
    public QueryResponseResult findAllCompanyRanking(ParamBean paramBean){
        try {
            PageInfo<CompanyRankF> companyRankFS= companyRankingService.findAllCompanyRanking(paramBean);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(companyRankFS);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询公益之星异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "添加排行")
    @PostMapping(value = "/addCompanyRanking")
    private QueryResponseResult addCompanyRanking(@RequestBody CompanyRankF companyRankF){
        try {
            companyRankingService.addCompanyRanking(companyRankF);
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("添加公益之星排行异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "通过id查询排行榜详情")
    @GetMapping(value = "/findCompanyRankingItemById/{companyRankingId}")
    @ApiImplicitParam(name = "companyRankingId",value = "排行榜id")
    private QueryResponseResult findCompanyRankingItemById(@PathVariable(name = "companyRankingId") String id){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(companyRankingService.findCompanyRankingItemById(id));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询排行榜详情异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
