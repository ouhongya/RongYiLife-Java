package com.rysh.module.pay.controller.clientController;

import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.commonService.beans.WxpayRequest;
import com.rysh.module.commonService.service.WxpayService;
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

import javax.smartcardio.CommandAPDU;

@Api(description = "微信支付接口")
@RestController
@Log4j2
@RequestMapping("/client/wxpay")
public class WxpayController implements ClientUserControllerApi {

    @Autowired
    private WxpayService service;

    @PostMapping("/order")
    @ApiOperation("step1【产生预付订单信息】")
    public QueryResponseResult makePreOrder(WxpayRequest request){
        try {
            String preOrderInfo = service.makePreOrder(request);
            QueryResult<String> result = new QueryResult<>();
            result.setData(preOrderInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (RuntimeException e) {
            log.error("产生预订单时参数异常 总金额{}",request.getAmount());
            return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
        }
    }

}
