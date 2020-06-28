package com.rysh.module.serverCompanyRanking.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankS;
import com.rysh.module.serverCompanyRanking.mapper.CompanyRankingMapper;
import com.rysh.module.serverCompanyRanking.service.CompanyRankingService;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompanyRankingServiceImpl implements CompanyRankingService {

    @Autowired
    private CompanyRankingMapper companyRankingMapper;

    @Override
    public PageInfo<CompanyRankF> findAllCompanyRanking(ParamBean paramBean) throws Exception {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        List<CompanyRankF> companyRankFS=companyRankingMapper.findAllCompanyRanking();
        PageInfo<CompanyRankF> pageInfo = new PageInfo<>(companyRankFS);
        return pageInfo;
    }

    @Override
    public void addCompanyRanking(CompanyRankF companyRankF) throws Exception{
        //将历史排行设为过期
        companyRankingMapper.updateCompanyRankingStatus(0);
        companyRankingMapper.updateCompanyRankingItemStatus(0);
        //获取当前操作人
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String id = GenerateUUID.create();
        //添加排行榜
        companyRankingMapper.addCompanyRanking(id,companyRankF,new Date(),name);
        //添加进入排行榜的人
        for (CompanyRankS companyRankS : companyRankF.getCompanyRankSs()) {
            companyRankingMapper.addCompanyRankingItem(GenerateUUID.create(),companyRankS,id,new Date());
        }
    }

    @Override
    public CompanyRankF findCompanyRankingItemById(String id) throws Exception {
        List<CompanyRankS> companyRankS = companyRankingMapper.findCompanyRankingItemByrankingCompanyId(id);
        CompanyRankF companyRankF=companyRankingMapper.findCompanyFById(id);
        //查询封面图和规则
       companyRankF.setCompanyRankSs(companyRankS);
        return companyRankF;
    }
}
