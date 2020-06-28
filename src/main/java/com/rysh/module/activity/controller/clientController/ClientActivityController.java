package com.rysh.module.activity.controller.clientController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.activity.beans.*;
import com.rysh.module.activity.service.ActivityService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/activity")
@Api(description = "活动用户报名")
@Log4j2
public class ClientActivityController implements ClientUserControllerApi {

    @Autowired
    private ActivityService service;

    @Autowired
    private CheckRedisTokenUtils tokenUtils;

    @ApiOperation("手机用户报名 【参数】=> ActivityId：活动id, name:用户填写的名字， phone：用户填写的手机号 token:用户token")
    @PostMapping("/join")
    public QueryResponseResult activityApply(@RequestBody ActivityUser activityUser){
        String userId = tokenUtils.checkRedisToken(activityUser.getToken());
        if (userId == null){
            return new QueryResponseResult(CommonCode.TOKEN_ERROR);
        }
        int row = service.userSignUp(activityUser,userId);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("===添加活动失败===");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    // TODO: 2019/11/11 地区
    @ApiOperation("查询所有的活动")
    @GetMapping("/all")
    public QueryResponseResult all(ParamBean paramBean){
        PageInfo<ActivityVo> activityList = null;
        try {
            activityList = service.all(paramBean,0);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        QueryResult<ActivityVo> result = new QueryResult<>();
        result.setData(activityList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation(value = "家庭之星排行榜",response =ClientRankResponse.class )
    @GetMapping("/rank/family")
    public QueryResponseResult familyRank(){
        try {
            ClientRankResponse rankList = service.clientFamilyRank();
            QueryResult<ClientRank> result = new QueryResult<>();
            result.setData(rankList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("客户端获取 家庭之星排行榜数据错误"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

}
