package com.rysh.module.statistics.mapper;

import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.statistics.beans.RYSHPrice;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersStatisticsMapper {
    List<OrdersSplit> findMarketByTime(@Param("belongType") Integer belongType, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<OrdersSplit> findOrdersByMarketId(@Param("marketId") String marketId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer findOrdersItemCountBySplitId(String id);

    Integer findOrdersCount(@Param("marketId") String marketId,@Param("sTime") String time,@Param("eTime") String format);

    Integer findOrdersCountByMarketId(String id);

    BigDecimal findSumOrdersPriceByTime(@Param("marketId") String id, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<RYSHPrice> findSumPrice(@Param("startTime") String startTime,@Param("endTime") String endtTime);

    Integer findShopOrdersCount(String id);

    BigDecimal findSumShopPriceByTime(@Param("shopCategoryId") String shopCategoryId,@Param("startTime") String startTime, @Param("endTime") String endTime);

    BigDecimal findMoneyCount(@Param("marketId") String marketId, @Param("startTime") String time, @Param("endTime") String format);
}
