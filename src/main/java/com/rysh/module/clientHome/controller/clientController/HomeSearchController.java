package com.rysh.module.clientHome.controller.clientController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.clientHome.beans.SearchResponse;
import com.rysh.module.clientHome.service.HomeSearchService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequestMapping(value = "/client/home")
@RestController
@Api(description = "主页信息接口")
public class HomeSearchController implements ClientUserControllerApi {

    @Autowired
    private HomeSearchService service;

    @ApiOperation(value = "首页搜索",notes = "search搜索内容")
    @PostMapping("/search")
    public QueryResponseResult homeSearch(@RequestBody ParamBean paramBean){
        try {
            PageInfo<SearchResponse> searchList = service.homeSearch(paramBean);
            QueryResult<PageInfo<SearchResponse>> result = new QueryResult<>();
            result.setData(searchList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("首页搜索失败->"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }







}
