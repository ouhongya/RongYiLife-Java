package com.rysh.module.statistics.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.UserDetail;

public interface StatisticsService {
    /**
     * 统计每日用户注册数量
     * @param startTime
	 * @param endTime 
     * @return com.rysh.module.statistics.beans.DayUserRig
     * @author Hsiang Sun
     * @date 2019/11/21 14:39
     */
    DayUserRig getDailyUserRegistration(String startTime, String endTime);

    /**
     * 统计该时间段内注册用户的详细信息
     * @param startTime
	 * @param endTime
     * @return com.github.pagehelper.PageInfo<com.rysh.module.statistics.beans.UserDetail>
     * @author Hsiang Sun
     * @date 2019/11/22 9:32
     */
    PageInfo<UserDetail> getDailyUserRegistrationDetail(ParamBean paramBean, String startTime, String endTime);
}
