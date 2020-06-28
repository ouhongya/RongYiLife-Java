package com.rysh.module.statistics.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.JoinedUser;

public interface ActivityStatisticsService {

    /**
     * 查询一段时间内的活动举行场次
     * @param startTime
	 * @param endTime
     * @return com.rysh.module.statistics.beans.DayUserRig
     * @author Hsiang Sun
     * @date 2019/11/22 14:29
     */
    DayUserRig getActivityStatistics(String startTime, String endTime);

    /**
     * 查询时间范围内的活动详情
     * @param paramBean
	 * @param startTime
	 * @param endTime
     * @return com.github.pagehelper.PageInfo<com.rysh.module.statistics.beans.ActivityDetail>
     * @author Hsiang Sun
     * @date 2019/11/22 15:10
     */
    PageInfo<ActivityDetail> getActivityStatisticsDetail(ParamBean paramBean, String startTime, String endTime);
    
    /**
     * 获取当前活动的参加用户
     * @param id 
     * @return com.github.pagehelper.PageInfo<com.rysh.module.statistics.beans.JoinedUser>
     * @author Hsiang Sun
     * @date 2019/11/22 17:13
     */
    PageInfo<JoinedUser> getActivityJoinUser(String id,ParamBean paramBean);
}
