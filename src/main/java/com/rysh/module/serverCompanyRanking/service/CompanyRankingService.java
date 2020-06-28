package com.rysh.module.serverCompanyRanking.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankS;

import java.util.List;

public interface CompanyRankingService {
    PageInfo<CompanyRankF> findAllCompanyRanking(ParamBean paramBean) throws Exception;

    void addCompanyRanking(CompanyRankF companyRankF) throws Exception;

    CompanyRankF findCompanyRankingItemById(String id) throws Exception;
}
