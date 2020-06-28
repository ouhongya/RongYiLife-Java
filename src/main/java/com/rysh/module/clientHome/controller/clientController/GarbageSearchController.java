package com.rysh.module.clientHome.controller.clientController;

import com.rysh.module.clientHome.beans.ClientGarbage;
import com.rysh.module.clientHome.service.HomeSearchService;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequestMapping(value = "/client/garbage")
@RestController
@Api(description = "客户端垃圾搜索")
public class GarbageSearchController {

    @Autowired
    private HomeSearchService service;

    @Autowired
    private CheckRedisTokenUtils tokenUtils;

    @ApiOperation(value = "通过搜索内容响应垃圾分类",notes = "name:垃圾名字，token:用户token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name",value = "搜索内容",required = true),
            @ApiImplicitParam(name = "token",value = "用户token",required = true)
    })
    @PostMapping("/search")
    public QueryResponseResult garbageSearch(String name,String token){
        try {
            String userId = tokenUtils.checkRedisToken(token);
            List<ClientGarbage> results = service.searchGarbage(name,userId);
            QueryResult<ClientGarbage> result = new QueryResult<>();
            result.setData(results);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("客户端垃圾搜索失败 当前用户token：{}",token);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
