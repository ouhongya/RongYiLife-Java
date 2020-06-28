package com.rysh.module.serverOrders.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.api.clientControllerApi.ClientUserControllerApi;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverOrders.beans.Delivers;
import com.rysh.module.serverOrders.beans.SuperHero;
import com.rysh.module.serverOrders.service.BackGroundOrdersService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


@Log4j2
@RestController
@RequestMapping("/server/storeorder")
@Api(description = "PC端商铺订单接口")
public class StoreOrdersController implements ClientUserControllerApi {
    @Autowired
    private BackGroundOrdersService backGroundOrdersService;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 后台查询订单
     * @param companyId   店铺id
     * @param totalState  0状态查询  1模糊订单号  2模糊手机号  3时间区间查询
     * @param state    订单状态  1=已支付  2=已发货 3=交易成功
     * @return
     */
    @GetMapping(value = "/findOrdersByCompanyId/{companyId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findOrdersByCompanyId(Integer totalState,@PathVariable(name = "companyId") String companyId, Integer state, ParamBean paramBean){
        try {
            PageInfo<OrdersSplit> ordersSplits = backGroundOrdersService.findOrdersByCompanyId(totalState,companyId, state,paramBean);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(ordersSplits);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("后台查询订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过id查询子订单明细
     * @param ordersSplitId
     * @return
     */
    @GetMapping(value = "/findOrdersSplitById/{ordersSplitId}")
    public QueryResponseResult findOrdersSplitById(@PathVariable(name = "ordersSplitId") String ordersSplitId){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(backGroundOrdersService.findOrdersSplitById(ordersSplitId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过id查询子订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 商品发货
     * @param delivers
     * @return
     */
    @PostMapping(value = "/ordersDeliver")
    public QueryResponseResult ordersDeliver(@RequestBody Delivers[] delivers){
        try {
            backGroundOrdersService.updateOrdersSplitState(Arrays.asList(delivers));
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("订单发货异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 通过子订单id查询收货人信息
     * @param ids
     * @return
     */
    @PostMapping(value = "/findShopAddressBySplitId")
    public QueryResponseResult findShopAddressBySplitId(@RequestBody String[] ids){
        try {
            SuperHero superHero = backGroundOrdersService.findShopAddressBySplitId(Arrays.asList(ids));
            QueryResult<Object> result = new QueryResult<>();
            result.setData(superHero);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过子订单id查询收货人信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

}
