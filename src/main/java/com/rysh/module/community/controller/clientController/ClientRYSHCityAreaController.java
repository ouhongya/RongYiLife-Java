package com.rysh.module.community.controller.clientController;

import com.rysh.module.community.beans.ClientArea;
import com.rysh.module.community.beans.ClientCity;
import com.rysh.module.community.service.ClientRYSHCityAreaService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(description = "移动端农场农庄区域查询")
@Log4j2
@RestController
@RequestMapping(value = "/client/FGRegion")
public class ClientRYSHCityAreaController {

    @Autowired
    private ClientRYSHCityAreaService clientRYSHCityAreaService;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询所有绑定了农场的城市
     * @return
     */
    @ApiOperation(value = "查询所有绑定了农场的城市",httpMethod = "POST",response = ClientCity.class)
    @PostMapping(value = "/findFarmCity")
    public QueryResponseResult findFarmCity(){
        try {
            List<ClientCity> clientCities=clientRYSHCityAreaService.findFarmCity();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(clientCities);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询农场城市异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过城市id查询该城市下绑定了农场的区域
     * @param cityId
     * @return
     */
    @ApiOperation(value = "通过城市id查询该城市下绑定了农场的区域",httpMethod = "POST",response = ClientArea.class)
    @ApiImplicitParam(name = "cityId",value = "城市id")
    @PostMapping(value = "/findFarmAreaByCityId")
    public QueryResponseResult findFarmAreaByCityId(String cityId){
        try {
            List<ClientArea> clientAreas=clientRYSHCityAreaService.findFarmAreaByCityId(cityId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(clientAreas);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过城市id查询绑定了农场的区域异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 查询所有绑定了农庄的城市
     * @return
     */
    @ApiOperation(value = "查询所有绑定了农庄的城市",httpMethod = "POST",response = ClientCity.class)
    @PostMapping(value = "/findGrangeCity")
    public QueryResponseResult findGrangeCity(){
        try {
            List<ClientCity> clientCities=clientRYSHCityAreaService.findGrangeCity();
            QueryResult<Object> result = new QueryResult<>();
            result.setData(clientCities);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询农庄城市异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过城市id查询该城市下绑定了农庄的区域
     * @param cityId
     * @return
     */
    @ApiOperation(value = "通过城市id查询该城市下绑定了农庄的区域")
    @ApiImplicitParam(name = "cityId",value = "城市id")
    @PostMapping(value = "/findGrangeAreaByCityId")
    public QueryResponseResult findGrangeAreaByCityId(String cityId){
        try {
            List<ClientArea> clientAreas = clientRYSHCityAreaService.findGrangeAreaByCityId(cityId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(clientAreas);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过城市id查询绑定了农庄的区域异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
