package com.rysh.module.serverCustomerQa.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCustomerQa.beans.CustomerQaPlus;



public interface CustomerQaPlusService {
    PageInfo<CustomerQaPlus> findAllCustomerQa(ParamBean paramBean) throws Exception;

    void addCustomerQa(CustomerQaPlus customerQaPlus) throws Exception;

    void updateCustomerQaPlus(CustomerQaPlus customerQaPlus) throws Exception;

    void UpperLower(String id, Integer status) throws Exception;

    void deleteCustomerQa(String id) throws Exception;

    void updateSort(String id, int valueInt);
}
