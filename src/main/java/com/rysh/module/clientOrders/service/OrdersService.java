package com.rysh.module.clientOrders.service;

import com.rysh.module.clientOrders.beans.FalseOrders;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersIdAndRealPay;
import com.rysh.module.commonService.beans.ParamBean;

import java.math.BigDecimal;
import java.util.List;


public interface OrdersService {

    OrdersIdAndRealPay addOrders(Orders orders) throws Exception;

    void updateState(String ordersId,Integer payWay);

    List<Orders> findOrdersByUserId(String userId,Integer ordersState,Integer ordersSplitStatus,Integer ordersSplitState) throws Exception;

    List<Orders> findAllOrdersByUserId(String userId, ParamBean paramBean);

    Boolean cancelOrders(String uid,String ordersId) throws Exception;

    void assessmentMarket(String splitId, Integer score) throws Exception;

    BigDecimal findScoreByMarketId(String marketId);

    Orders findOrdersByGoods(List<FalseOrders> falseOrders);

    Boolean acceptGoods(String uid, String ordersSplitId) throws Exception;

    String remindDeliverGoods(String uid,String ordersSplitId) throws Exception;
}
