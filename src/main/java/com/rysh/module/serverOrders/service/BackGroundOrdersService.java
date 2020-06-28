package com.rysh.module.serverOrders.service;


import com.github.pagehelper.PageInfo;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverOrders.beans.Delivers;
import com.rysh.module.serverOrders.beans.ImIronMan;
import com.rysh.module.serverOrders.beans.SuperHero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BackGroundOrdersService {
    PageInfo<OrdersSplit> findOrdersByCompanyId(Integer totalState,String companyId, Integer state, ParamBean paramBean) throws Exception;

    ImIronMan findOrdersSplitById(String splitId);

    void updateOrdersSplitState(List<Delivers> delivers) throws Exception;

    SuperHero findShopAddressBySplitId(List<String> ids) throws Exception;
}
