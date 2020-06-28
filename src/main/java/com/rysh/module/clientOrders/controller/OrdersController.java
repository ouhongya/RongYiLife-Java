package com.rysh.module.clientOrders.controller;

import com.alibaba.fastjson.JSONObject;
import com.rysh.module.clientOrders.beans.FalseOrders;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.clientOrders.service.OrdersService;
import com.rysh.module.commonService.beans.ParamBean;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RequestMapping(value = "/client/order")
@RestController
@Api(description = "移动端订单接口")
public class OrdersController {
    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private OrdersService  ordersService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;


    /**
     * 添加 拆分订单
     * @param ordersMap
     * @return
     */
    @ApiOperation(value = "添加订单",httpMethod = "POST")
    @PostMapping(value = "/addOrders")
    public QueryResponseResult addOrders(@RequestParam Map<String,String> ordersMap){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(ordersMap.get("token"));
            if(uid!=null){
                Orders orders = new Orders();
                orders.setUserId(uid);
                orders.setName(ordersMap.get("name"));
                orders.setPhone(ordersMap.get("phone"));
                orders.setAddress(ordersMap.get("address"));
                orders.setRealPay(new BigDecimal(ordersMap.get("realPay")));
                List<OrdersSplit> ordersSplits = JSONObject.parseArray(ordersMap.get("ordersSplits"), OrdersSplit.class);
                orders.setOrdersSplits(ordersSplits);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(ordersService.addOrders(orders));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e) {
            log.error("添加订单异常");
            log.error(e.getMessage());
            log.error(e);
            log.error(sf.format(new Date()));
            if(e.getMessage()==null){
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }else {
                return new QueryResponseResult(CommonCode.ORDERS_ERROR);
            }
        }
    }
    /**
     * 按条件查询订单
     * @param token
     * @param ordersState 1未支付 2已支付  3取消
     * @param ordersSplitStatus 0未支付  1已支付
     * @param ordersSplitState 1 未发货  2已发货 3交易完成
     * @return
     */
    @ApiOperation(value = "按条件查询订单",httpMethod = "POST",response = Orders.class,notes = "token  ordersState(订单状态：1未支付 2已支付 3取消)  ordersSplitStatus(子订单支付状态：0未支付  1已支付)  ordersSplitState(子订单支付完成后的状态：1 未发货  2已发货 3交易完成)")
    @ApiImplicitParams(
            {
             @ApiImplicitParam(name = "ordersState",value = "订单状态：1未支付 2已支付 3取消"),
             @ApiImplicitParam(name = "ordersSplitStatus",value = "子订单支付状态：0未支付  1已支付"),
             @ApiImplicitParam(name = "ordersSplitState",value = "子订单支付完成后的状态：1 未发货  2已发货 3交易完成")
            }
    )
    @PostMapping(value = "/findOrdersByUserId")
    public QueryResponseResult findOrdersByUserId(String token,Integer ordersState,Integer ordersSplitStatus,Integer ordersSplitState){
        try {
            if(token==null){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            if(ordersState==null||(ordersState!=1&&ordersState!=2&&ordersState!=3)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            if(ordersSplitStatus==null||(ordersSplitStatus!=0&&ordersSplitStatus!=1)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            if(ordersSplitState==null||(ordersSplitState!=1&&ordersSplitState!=2&&ordersSplitState!=3)){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<Orders> orders = ordersService.findOrdersByUserId(uid,ordersState,ordersSplitStatus,ordersSplitState);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(orders);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        } catch (Exception e) {
            log.error("按条件查询订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 查询所有订单
     * @param token
     * @return
     */
    @ApiOperation(value = "查询用户所有订单",httpMethod = "POST",response = Orders.class,notes = "只需要传 token   page(当前是第几页)  size(每页显示几条记录)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
            @ApiImplicitParam(name = "page",value = "当前页数"),
            @ApiImplicitParam(name = "size",value = "每页显示记录数")
    })
    @PostMapping(value = "/findAllOrdersByUserId")
    public QueryResponseResult findAllOrdersByUserId(String token, ParamBean paramBean){
        try {
            if(token==null){
                return new QueryResponseResult(CommonCode.PARAMETER_ERROR);
            }
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                QueryResult<Object> result = new QueryResult<>();
                result.setData(ordersService.findAllOrdersByUserId(uid,paramBean));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询所有订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 取消订单
     * @param ordersId
     * @return
     */
    @ApiOperation(value = "取消订单",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
            @ApiImplicitParam(name = "ordersId",value = "订单id")
    })
    @PostMapping(value = "/cancelOrders")
    public QueryResponseResult cancelOrders(String token,String ordersId){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                if(ordersService.cancelOrders(uid, ordersId)){
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }else {
                    return new QueryResponseResult(CommonCode.GET_OUT);
                }
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("取消订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 给店铺评分
     * @param splitId  子订单id
     * @param score    分数
     * @return
     */
    @ApiOperation(value = "给店铺评分",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "splitId",value = "子订单id"),
            @ApiImplicitParam(name = "score",value = "分数")
    })
    @PostMapping(value = "/assessmentMarket")
    public QueryResponseResult assessmentMarket(String splitId,Integer score){
        try {
            ordersService.assessmentMarket(splitId,score);
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("给店铺评分异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 通过店铺id查询店铺的评价分数
     * @param marketId
     * @return
     */
    @ApiOperation(value = "查询店铺的评价分数",httpMethod = "POST")
    @ApiImplicitParam(name = "marketId",value = "店铺id")
    @PostMapping(value = "/findScoreByMarketId")
    public QueryResponseResult findScoreByMarketId(String marketId){
        try {
            BigDecimal avgScore = ordersService.findScoreByMarketId(marketId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(avgScore);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询店铺评分异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 生成虚拟订单
     * @param falseOrders
     * @return
     */
    @ApiOperation(value = "通过商品id和商品类型生成虚拟订单",httpMethod = "POST",response = Orders.class)
    @PostMapping(value = "/findFalseOrders")
    public QueryResponseResult findFalseOrders(List<FalseOrders> falseOrders){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(ordersService.findOrdersByGoods(falseOrders));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询虚拟订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 确认收货
     * @param token
     * @param ordersSplitId
     * @return
     */
    @ApiOperation(value = "确认收货",httpMethod = "POST")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "token",value = "token"),
                    @ApiImplicitParam(name = "ordersSplitId",value = "子订单id")
            }
    )
    @PostMapping(value = "/acceptGoods")
    public QueryResponseResult acceptGoods(String token,String ordersSplitId){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                ordersService.acceptGoods(uid,ordersSplitId);
                return new QueryResponseResult(CommonCode.SUCCESS);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("确认收货异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 提醒发货
     * @param token
     * @param ordersSplitId
     * @return
     */
    @PostMapping(value = "/remindDeliverGoods")
    @ApiOperation(value = "提醒发货",httpMethod = "POST")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "token",value = "token"),
                    @ApiImplicitParam(name = "ordersSplitId",value = "子订单id")
            }
    )
    public QueryResponseResult remindDeliverGoods(String token,String ordersSplitId){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
               String msg= ordersService.remindDeliverGoods(uid,ordersSplitId);
               if("滚".equals(msg)){
                   return new QueryResponseResult(CommonCode.GET_OUT);
               }else {
                   QueryResult<Object> result = new QueryResult<>();
                   result.setData(msg);
                   return new QueryResponseResult(CommonCode.SUCCESS,result);
               }
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("提醒发货异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
