package com.rysh.module.serverOrders.mapper;

import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.serverOrders.beans.CompanyIdAndState;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BackGroundOrdersMapper {
    List<OrdersSplit> findOrdersByCompanyId(CompanyIdAndState cs);

    List<OrdersSplit> findAllOrdersByCompanyId(String companyId);

    List<OrdersSplit> findOrdersByLikeAgainByCompanyId(@Param("companyId") String companyId, @Param("totalState") Integer totalState,@Param("search") String search);

    List<OrdersSplit> findOrdersByCreatedTime(@Param("companyId") String companyId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
