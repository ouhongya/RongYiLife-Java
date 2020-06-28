package com.rysh.module.community.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.beans.CommunityResponse;
import com.rysh.module.community.service.CommunityService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/community")

@Api(description = "服务端社区接口")
public class ServerCommunityController implements ServerUserControllerApi {

    @Autowired
    private CommunityService service;

    /*
     * 查询所有小区信息
     * @param start
	 * @param size
     * @return com.rysh.system.response.QueryResponseResult
     * @author Hsiang Sun
     * @date 2019/9/6 15:11
     */
    @ApiOperation("查询所有小区信息")
    @GetMapping("/all")
    public QueryResponseResult allCommunity(ParamBean param){
        PageInfo<Community> allCommunity = service.getAllCommunity(param.getPage(), param.getSize());
        QueryResult<Community> result = new QueryResult<>();
        result.setData(allCommunity);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("通过id查询社区 用于数据回显")
    @GetMapping("/info/{id}")
    public QueryResponseResult getCommunityById(@PathVariable String id){
        CommunityResponse community = service.findCommunityById(id);
        QueryResult<Community> result = new QueryResult<>();
        result.setData(community);
        if (community != null){
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新社区信息")
    @PostMapping("/update")
    public QueryResponseResult editCommunity(@RequestBody Community community){
        System.err.println(community.toString());
        int i = service.updateCommunity(community);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new  QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("新增社区")
    @PostMapping("/add")
    public QueryResponseResult addCommunity(@RequestBody Community community){
        int i = service.communityAdd(community);
        if (1 != i){
            return new QueryResponseResult(CommonCode.FAIL);
        }else {
            return new QueryResponseResult(CommonCode.SUCCESS);
        }
    }

    @ApiOperation("删除社区")
    @DeleteMapping("/delete/{communityId}")
    public QueryResponseResult deleteCommunity(@PathVariable(name = "communityId") String communityId){
        int i=service.deleteCommunity(communityId);
        if (1 != i){
            return new QueryResponseResult(CommonCode.FAIL);
        }else {
            return new QueryResponseResult(CommonCode.SUCCESS);
        }
    }
}
