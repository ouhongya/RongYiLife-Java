package com.rysh.module.community.controller.clientController;

import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.service.AreaService;
import com.rysh.module.community.service.CityService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/client/area")
@Api(description = "手机端区域接口")
public class ClientAreaController implements ClientUserControllerApi {

    @Autowired
    private AreaService service;

    @ApiOperation("根据城市id查询所有的区域")
    @PostMapping("/all")
    public QueryResponseResult findAreaByCityId(String id){
        try {
            List<ClientArea> areaList = service.getAreaByCityId(id);
            QueryResult<ClientArea> result = new QueryResult<>();
            result.setData(areaList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("手机端获取区域信息失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
