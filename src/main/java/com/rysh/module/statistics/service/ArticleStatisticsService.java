package com.rysh.module.statistics.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ArticleDetail;
import com.rysh.module.statistics.beans.DayUserRig;

public interface ArticleStatisticsService {
    /**
     * 文章统计
     * @param startTime
     * @param endTime
     * @return com.rysh.module.statistics.beans.DayUserRig
     * @author Hsiang Sun
     * @date 2019/11/25 9:34
     */
    DayUserRig articleStatistics(String startTime, String endTime);
    
    /**
     * 获取统计文章详情
     * @param paramBean
	 * @param startTime
	 * @param endTime 
     * @return com.github.pagehelper.PageInfo<com.rysh.module.statistics.beans.ArticleDetail>
     * @author Hsiang Sun
     * @date 2019/11/25 10:29
     */
    PageInfo<ArticleDetail> getArticleStatisticsDetail(ParamBean paramBean, String startTime, String endTime);
}
