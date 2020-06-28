package com.rysh.module.clientCustomerQa.controller;

import com.rysh.module.clientCustomerQa.beans.CusTomerQa;
import com.rysh.module.clientCustomerQa.service.CusTomerQaService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequestMapping(value = "/client/CusTomerQa")
@RestController
@Log4j2
@Api(description = "移动端客服问答")
public class CusTomerQaController {

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CusTomerQaService cusTomerQaService;

    @ApiOperation(value = "查询客服问答",notes = "只需要page  和 size两个分页参数")
    @PostMapping(value = "/findALLCusTomerQa")
    public QueryResponseResult findALLCusTomerQa(ParamBean paramBean){
        try {
            List<CusTomerQa> cusTomerQas = cusTomerQaService.findAllCusTomerQa(paramBean);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(cusTomerQas);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询客服问答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
