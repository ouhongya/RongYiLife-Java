package com.rysh.module.serverCompanyRanking.mapper;

import com.rysh.module.serverCompanyRanking.beans.CompanyRankF;
import com.rysh.module.serverCompanyRanking.beans.CompanyRankS;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CompanyRankingMapper {
    List<CompanyRankF> findAllCompanyRanking();

    void updateCompanyRankingStatus(Integer status);

    void updateCompanyRankingItemStatus(Integer status);

    void addCompanyRanking(@Param("id") String id,@Param("companyRankF") CompanyRankF companyRankF,@Param("date") Date date,@Param("name") String name);

    void addCompanyRankingItem(@Param("id") String id,@Param("companyRankS") CompanyRankS companyRankS,@Param("Fid") String Fid,@Param("date") Date date);

    List<CompanyRankS> findCompanyRankingItemByrankingCompanyId(String id);

    CompanyRankF findNewestRanking();

    CompanyRankF findCompanyFById(String id);
}
