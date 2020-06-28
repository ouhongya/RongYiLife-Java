package com.rysh.module.community.controller.clientController;


import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.community.beans.ClientCommunity;
import com.rysh.module.community.service.CommunityService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/client/community")
@Api(description = "手机端社区接口")
public class ClientCommunityController implements ClientUserControllerApi {

    @Autowired
    private CommunityService service;

    
    /**
     * 
     * @param areaId
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/8/23 15:32
     */
    @ApiOperation("根据区域id查询所有的小区信息")
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public QueryResponseResult getAllCommunity(String areaId){
        List<ClientCommunity> communityList = null;
        try {
            communityList = service.getAllCommunityByAreaId(areaId);
            QueryResult<ClientCommunity> result = new QueryResult();
            result.setData( communityList );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("手机端获取小区信息失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
