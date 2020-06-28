package com.rysh.module.clientSuperActivity.controller;

import com.rysh.module.activity.beans.ActivityVo;
import com.rysh.module.clientSuperActivity.beans.ActivityPlus;
import com.rysh.module.clientSuperActivity.service.SuperActivityService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(description = "移动端活动")
@Log4j2
@RestController
@RequestMapping(value = "/client/superActivity")
public class SuperActivityController {

    @Autowired
    private SuperActivityService superActivityService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @ApiOperation(value = "搜索活动",httpMethod = "POST",notes = "查询城市所有活动 areaId可以不要   查询区域的活动 cityId可以不要  search是搜索关键字 （可以没有）",response = ActivityVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
            @ApiImplicitParam(name = "cityId",value = "城市id"),
            @ApiImplicitParam(name = "areaId",value = "区域id")
    })
    @PostMapping(value = "/findSuperActivity")
    public QueryResponseResult findSuperActivity(String token,ParamBean paramBean,String cityId,String areaId){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if (uid != null) {
                List<ActivityPlus> activities=superActivityService.findSuperActivity(uid,paramBean,cityId,areaId);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(activities);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }

        }catch (Exception e){
            log.error("活动搜索异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "活动报名",httpMethod = "POST",notes = "返回操作成功 = 报名成功   操作失败=报名失败")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
            @ApiImplicitParam(name = "activityId",value = "活动id"),
            @ApiImplicitParam(name = "name",value = "报名人名字"),
            @ApiImplicitParam(name = "phone",value = "报名人电话")
    })
    @PostMapping(value = "/activitySignUp")
    public QueryResponseResult activitySignUp(String token,String activityId,String name,String phone){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                if(superActivityService.activitySignUp(uid,activityId,name,phone)){
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }else {
                    return new QueryResponseResult(CommonCode.FAIL);
                }
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("活动报名异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "查询活动的城市和区域",httpMethod = "POST",notes = "cityId传了 是查城市下有活动的区域  不传是查所有有活动的城市")
    @ApiImplicitParam(name = "cityId",value = "城市id")
    @PostMapping(value = "/findActivityCity")
    public QueryResponseResult findActivityCityArea(String cityId){
        try {
            if(cityId==null||"".equals(cityId)){
                List<City> cities=superActivityService.findActivityCity();
                QueryResult<Object> result = new QueryResult<>();
                result.setData(cities);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                List<Area> areas=superActivityService.findActivityAreaByCityId(cityId);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(areas);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }
        }catch (Exception e){
            log.error("查询活动城市异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
