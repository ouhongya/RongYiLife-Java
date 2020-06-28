package com.rysh.module.community.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.service.CityService;
import com.rysh.module.community.service.impl.CityServiceImpl;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/server/city")
public class ServerCityController {

    @Autowired
    private CityService service;

    @PostMapping("/add")
    @ApiOperation(value = "新增城市 请传入城市名称:name")
    public QueryResponseResult addCity(String name){
        int row = service.addCity(name);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "更新城市名 传入城市id和城市名称 ")
    @PutMapping("/update/{id}")
    public QueryResponseResult updateCityName(@PathVariable String id,String newName){
        try {
            service.updateCity(id,newName);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
           log.error("更新城市时出错啦"+e);
           return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("所有的城市列表")
    @GetMapping("/all")
    public QueryResponseResult getAllCities(){
        try {
            List<City> cityList = service.getAllCity();
            QueryResult<City> result = new QueryResult<>();
            result.setData(cityList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("服务端获取所有城市列表失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }

    }

    @GetMapping("/tree")
    public QueryResponseResult cityAndAreaTree(){
        List<City> cityTree = null;
        try {
            cityTree = service.getCityAndAreaTree();
            QueryResult<City> result = new QueryResult<>();
            result.setData(cityTree);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @GetMapping("/tree/community")
    public QueryResponseResult cityAndAreaAndCommunityTree(){
        List<City> cityTree = null;
        try {
            cityTree = service.getCityAndAreaAndCommunityTree();
            QueryResult<City> result = new QueryResult<>();
            result.setData(cityTree);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

}
