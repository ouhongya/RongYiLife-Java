package com.rysh.module.community.controller.clientController;

import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.community.beans.ClientCity;
import com.rysh.module.community.service.CityService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/city")
@Log4j2
@Api(description = "手机端城市接口")
public class ClientCityController implements ClientUserControllerApi {

    @Autowired
    private CityService cityService;

    @ApiOperation("所有的城市列表")
    @GetMapping("/all")
    public QueryResponseResult getAllCities(){

        List<ClientCity> cities = null;
        try {
            cities = cityService.getClientCity();
            QueryResult result =  new QueryResult();
            result.setData( cities );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("手机端获取所有城市失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

}
