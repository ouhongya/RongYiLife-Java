package com.rysh.module.clientCustomerQa.service.impl;

import com.github.pagehelper.PageHelper;
import com.rysh.module.clientCustomerQa.beans.CusTomerQa;
import com.rysh.module.clientCustomerQa.mapper.CusTomerQaMapper;
import com.rysh.module.clientCustomerQa.service.CusTomerQaService;
import com.rysh.module.commonService.beans.ParamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CusTomerQaServiceImpl implements CusTomerQaService {

    @Autowired
    private CusTomerQaMapper cusTomerQaMapper;

    @Override
   public List<CusTomerQa> findAllCusTomerQa(ParamBean paramBean) throws Exception{
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
       return cusTomerQaMapper.findAllCusTomerQa();

   }
}
