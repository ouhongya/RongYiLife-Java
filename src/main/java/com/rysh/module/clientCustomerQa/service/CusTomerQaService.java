package com.rysh.module.clientCustomerQa.service;

import com.rysh.module.clientCustomerQa.beans.CusTomerQa;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface CusTomerQaService {
    List<CusTomerQa> findAllCusTomerQa(ParamBean paramBean) throws Exception;
}
