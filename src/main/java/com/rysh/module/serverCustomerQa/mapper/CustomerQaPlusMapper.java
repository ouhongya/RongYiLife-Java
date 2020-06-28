package com.rysh.module.serverCustomerQa.mapper;

import com.rysh.module.serverCustomerQa.beans.CustomerQaPlus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CustomerQaPlusMapper {
    List<CustomerQaPlus> findAllCustomerQa();

    void addCustomerQa(@Param("customerQaPlus") CustomerQaPlus customerQaPlus, @Param("name") String name, @Param("date") Date date);

    void updateCustomerQaPlus(@Param("customerQaPlus") CustomerQaPlus customerQaPlus, @Param("name") String name);

    void updateCustomerQaUpperLower(@Param("customerQaId") String id, @Param("status") Integer status, @Param("name") String name);

    void deleteCustomerQa(String id);

    void updateSort(@Param("id")String id,@Param("value") int valueInt);
}
