package com.rysh.module.clientOrders.mapper;

import com.rysh.module.clientOrders.beans.*;
import com.rysh.module.serverOrders.beans.ShopAddressResult;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public interface OrdersMapper {

    void addOrdersItem(OrdersItem ordersItem);

    void addOrders(Orders orders);

    void addOrdersSplit(OrdersSplit ordersSplit);

    void updateOrdersState(@Param("so") StateAndOrdersId so, @Param("payWay") Integer payWay,@Param("payTime") Date payTime);

    void updateOrdersSplitStatus(StateAndOrdersId so);

    List<Orders> findOrdersByUserIdAndState(@Param("userId") String userId,@Param("ordersState") Integer ordersState,@Param("ordersSplitStatus") Integer ordersSplitStatus,@Param("ordersSplitState") Integer ordersSplitState);

    List<OrdersSplit> findOrdersSplitByOrdersId(OrdersIdAndStateAndStatue oss);

    List<OrdersItem> findOrdersItemByOrdersSplitId(String id);

    List<Orders> findAllOrdersByUserId(String userId);

    List<OrdersSplit> findAllSplitByOrdersId(String id);

    Orders findOrdersByOrderId(String ordersId);

    OrdersSplit findOrdersSplitById(String splitId);

    void addOrdersSplitRealPay(@Param("splitPrice") BigDecimal ordersSplitPrice, @Param("splitId") String id);

    void updateOrdersSplitState(@Param("splitId") String splitId, @Param("state") int i);

    void updatecourierDataBySplitId(@Param("splitId") String splitId, @Param("shopAddressResult") ShopAddressResult shopAddressResult);

    Integer findItemCountByGoodsId(@Param("goodsId") String goodsId);

    Integer findSplitCountByMarketId(@Param("marketId") String marketId);

    void addScoreUser(@Param("scoreUser") ScoreUser scoreUser);

    List<String> findSplitIdsByMarketId(String marketId);

    Integer findScoreBySplitId(String splitId);

    List<String> findAllOrdersIdByUserId(String uid);

    List<OrdersSplit> findOrdersSplitByShopId(String marketId);

    List<Orders> findNotAutomaticCancelOrdersByUserId(String uid);

    List<OrdersSplit> findOrdersSplitByOrderId(String id);

    Orders findOrdersByOrderIdToScore(String ordersId);

    List<OrdersSplit> findOrdersSplitByOrderIdAndShopId(@Param("id") String id, @Param("marketId") String marketId);

    ScoreUser findScoreUser(@Param("ordersSplitId") String id,@Param("uid") String uid);

    Orders findOrdersByOrderIdToStatistics(String ordersId);
}
