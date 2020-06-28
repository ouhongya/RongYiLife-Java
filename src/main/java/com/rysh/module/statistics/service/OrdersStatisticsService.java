package com.rysh.module.statistics.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.*;

import java.util.List;
import java.util.Map;

public interface OrdersStatisticsService {
    PageInfo findOrdersMarket(Integer belongType, ParamBean paramBean,String startTime,String endTime) throws  Exception;

    PageInfo<OrdersOrdersSplitSS> findOrdersByMarketId(ParamBean paramBean, String marketId, String startTime, String endTime) throws Exception;

    Map<String,Object> findCountTable(String marketId, String startTime, String endTime) throws Exception;

    PageInfo<OrdersItemSS> findItemDetailBySplitId(String ordersSplitId,ParamBean paramBean) throws Exception;

    List<RYSHPrice> findSumPrice(String startTime, String endTime) throws Exception;
}
