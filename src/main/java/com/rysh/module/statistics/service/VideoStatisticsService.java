package com.rysh.module.statistics.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.VideoDetail;

public interface VideoStatisticsService {

    /**
     * 视频统计
     * @param startTime
	 * @param endTime
     * @return com.rysh.module.statistics.beans.DayUserRig
     * @author Hsiang Sun
     * @date 2019/11/25 11:54
     */
    DayUserRig getVideoStatistics(String startTime, String endTime);

    /**
     * 视频统计详情
     * @param paramBean
	 * @param startTime
	 * @param endTime
     * @return com.github.pagehelper.PageInfo<com.rysh.module.statistics.beans.ActivityDetail>
     * @author Hsiang Sun
     * @date 2019/11/25 14:50
     */
    PageInfo<VideoDetail> getVideoStatisticsDetail(ParamBean paramBean, String startTime, String endTime);
}
