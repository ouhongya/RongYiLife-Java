package com.rysh.module.statistics.controller;


import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.OrdersItemSS;
import com.rysh.module.statistics.beans.RYSHPrice;
import com.rysh.module.statistics.service.OrdersStatisticsService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@Api(description = "订单统计")
@RequestMapping(value = "/server/ordersStatistics")
public class OrdersStatisticsController {

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private OrdersStatisticsService ordersStatisticsService;

   @ApiOperation(value = "查询农场农庄商铺自营的销售总金额")
   @ApiImplicitParams({
           @ApiImplicitParam(name = "startTime",value = "起始时间"),
           @ApiImplicitParam(name = "endTime",value = "结束时间")
   })
   @GetMapping(value = "/findSumPrice")
   public QueryResponseResult findSumPrice(String startTime,String endTime){
       try {
           List<RYSHPrice> ryshPrice=ordersStatisticsService.findSumPrice(startTime,endTime);
           QueryResult<Object> result = new QueryResult<>();
           result.setData(ryshPrice);
           return new QueryResponseResult(CommonCode.SUCCESS,result);
       }catch (Exception e){
           log.error("查询农场农庄商铺自营的销售总金额异常");
           log.error(e);
           log.error(sf.format(new Date()));
           return new QueryResponseResult(CommonCode.SERVER_ERROR);
       }
   }




  @ApiOperation(value = "查询被统计的店铺")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "belongType",value = "1商铺  2农场  3农庄"),
          @ApiImplicitParam(name = "startTime",value = "开始时间"),
          @ApiImplicitParam(name = "endTime",value = "结束时间")
  })
  @GetMapping(value = "/findOrdersMarket")
    public QueryResponseResult findOrdersMarket(Integer belongType, ParamBean paramBean,String startTime,String endTime){
            try {
                PageInfo pageInfo= ordersStatisticsService.findOrdersMarket(belongType,paramBean,startTime,endTime);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(pageInfo);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }catch (Exception e){
                log.error("查询被统计的店铺异常");
                log.error(e);
                log.error(sf.format(new Date()));
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
    }


    @ApiOperation(value = "查询统计表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "marketId",value = "店铺id"),
            @ApiImplicitParam(name = "startTime",value = "起始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间")
    })
    @GetMapping(value = "/findCountTable")
    public QueryResponseResult findCountTable(String marketId,String startTime,String endTime){
      try {
          Map<String,Object> map=ordersStatisticsService.findCountTable(marketId,startTime,endTime);
          QueryResult<Object> result = new QueryResult<>();
          result.setData(map);
          return new QueryResponseResult(CommonCode.SUCCESS,result);
      }catch (Exception e){
          log.error("查询统计表异常");
          log.error(e);
          log.error(sf.format(new Date()));
          return new QueryResponseResult(CommonCode.SERVER_ERROR);
      }
    }

    @ApiOperation(value = "查询店铺下被统计的订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "marketId",value = "店铺id"),
            @ApiImplicitParam(name = "startTime",value = "开始时间"),
            @ApiImplicitParam(name = "endTime",value = "结束时间")
    })
    @GetMapping(value = "/findOrdersByMarketId")
    private QueryResponseResult findOrdersByMarketId(ParamBean paramBean,String marketId,String startTime,String endTime){
        try {
            QueryResult<Object> result = new QueryResult<>();
            result.setData(ordersStatisticsService.findOrdersByMarketId(paramBean,marketId, startTime, endTime));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询店铺下被统计的订单异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "查询子订单下的商品详情")
    @ApiImplicitParam(name = "ordersSplitId",value = "子订单id")
    @GetMapping(value = "/findItemDetailBySplitId")
    public QueryResponseResult findItemDetailBySplitId(String ordersSplitId,ParamBean paramBean){
      try {
          PageInfo<OrdersItemSS> pageInfo= ordersStatisticsService.findItemDetailBySplitId(ordersSplitId,paramBean);
          QueryResult<Object> result = new QueryResult<>();
          result.setData(pageInfo);
          return new QueryResponseResult(CommonCode.SUCCESS,result);
      }catch (Exception e){
          log.error("查询子订单下的商品详情异常");
          log.error(e);
          log.error(sf.format(new Date()));
          return new QueryResponseResult(CommonCode.SERVER_ERROR);
      }
    }
}

