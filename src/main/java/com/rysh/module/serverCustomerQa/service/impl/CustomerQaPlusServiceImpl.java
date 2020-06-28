package com.rysh.module.serverCustomerQa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCustomerQa.beans.CustomerQaPlus;
import com.rysh.module.serverCustomerQa.mapper.CustomerQaPlusMapper;
import com.rysh.module.serverCustomerQa.service.CustomerQaPlusService;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class CustomerQaPlusServiceImpl implements CustomerQaPlusService {

    @Autowired
    private CustomerQaPlusMapper customerQaPlusMapper;

    @Override
    public PageInfo<CustomerQaPlus> findAllCustomerQa(ParamBean paramBean) throws Exception {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageInfo<CustomerQaPlus> pageInfo = new PageInfo<>(customerQaPlusMapper.findAllCustomerQa());
        return pageInfo;
    }

    @Override
    public void addCustomerQa(CustomerQaPlus customerQaPlus) throws Exception {
        customerQaPlus.setId(GenerateUUID.create());
        //获取操作人用户名
        String name= SecurityContextHolder.getContext().getAuthentication().getName();
        customerQaPlusMapper.addCustomerQa(customerQaPlus,name,new Date());
    }

    @Override
    public void updateCustomerQaPlus(CustomerQaPlus customerQaPlus) throws Exception {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        customerQaPlusMapper.updateCustomerQaPlus(customerQaPlus,name);
    }

    @Override
    public void UpperLower(String id, Integer status) throws Exception{
        String name=SecurityContextHolder.getContext().getAuthentication().getName();
        customerQaPlusMapper.updateCustomerQaUpperLower(id,status,name);
    }

    @Override
    public void deleteCustomerQa(String id) throws Exception {
        customerQaPlusMapper.deleteCustomerQa(id);
    }

    @Override
    public void updateSort(String id, int valueInt) {
        customerQaPlusMapper.updateSort(id,valueInt);
    }
}
