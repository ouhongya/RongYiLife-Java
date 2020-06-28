package com.rysh.module.community.controller.serverController;

import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.service.AreaService;
import com.rysh.module.community.service.CityService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/server/area")
public class ServerAreaController {
    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    /**
     * 添加区域
     * @param cityName
     * @param areaName
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/8/26 9:38
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public QueryResponseResult addArea(String cityName, String areaName){

        City city = cityService.queryByCityName(cityName);
        if (city == null){
            return new QueryResponseResult(CommonCode.FAIL);
        }

        //查询城市和区域是否已经存在
        boolean isExists = areaService.checkHasExist(cityName,areaName);
        if (isExists){
            return new QueryResponseResult(CommonCode.FAIL);
        }

        try {
            areaService.addArea(cityName,areaName);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("添加区域失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }

    }

    @ApiOperation("区域信息更新 请传入区域id 和区域名字name")
    @PutMapping("/update")
    public QueryResponseResult updateAreaName(@RequestBody Area area){
        try {
            areaService.updateAreaName(area);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("区域更新失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 根据城市名获取当前该城市下的所有区域
     * @param cityId
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/8/26 9:37
     */
    @RequestMapping(value = "/all/{cityId}",method = RequestMethod.POST)
    public QueryResponseResult findAreaByCityId(@PathVariable String cityId){
        try {
            List<Area> areas = areaService.getAllAreaByCityId(cityId);
            QueryResult<Area> result = new QueryResult<>();
            result.setData(areas);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("根据城市id查询区域失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
