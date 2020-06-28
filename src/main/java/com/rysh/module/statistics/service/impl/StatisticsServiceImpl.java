package com.rysh.module.statistics.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.TimeCount;
import com.rysh.module.statistics.beans.UserDetail;
import com.rysh.module.statistics.mapper.StatisticsServiceMapper;
import com.rysh.module.statistics.service.StatisticsService;
import com.rysh.module.utils.TimeCale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsServiceMapper mapper;

    @Override
    public DayUserRig getDailyUserRegistration(String startTime, String endTime) {
        DayUserRig dayUserRig = new DayUserRig();

        Date endTimeDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            endTimeDate = sdf.parse(endTime);
        } catch (ParseException e) {
            log.error("时间转换出现错误{}",endTime);
        }
        long endTimeLong = endTimeDate.getTime() + 86400000;//加一天
        Date newEndTime = new Date(endTimeLong);
        String newEndTimeStr = sdf.format(newEndTime);


        List<TimeCount> timeList = null;
        try {
            timeList = TimeCale.timeList(startTime, endTime);
        } catch (ParseException e) {
            log.error("丁聪的时间工具报错啦！");
        }

        for (TimeCount timeCount : timeList) {
            List<TimeCount> user = mapper.findUserByTime(startTime, newEndTimeStr);
            for (TimeCount sqlCount : user) {
                if (sqlCount.getTime().equals(timeCount.getTime())){
                    timeCount.setCount(sqlCount.getCount());
                }
            }
        }

        dayUserRig.setCountList(timeList);
        return dayUserRig;
    }

    @Override
    public PageInfo<UserDetail> getDailyUserRegistrationDetail(ParamBean paramBean, String startTime, String endTimeStr) {
        String endTime = TimeCale.delayOneDay(endTimeStr);

        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<UserDetail> userDetails = mapper.findUserDetailByTime(startTime,endTime);

        for (UserDetail userDetail : userDetails) {
            UserDetail userCity = mapper.findUserCity(userDetail.getId());
            if (userCity == null){
                userDetail.setCommunity(null);
                userDetail.setArea(null);
                userDetail.setCity(null);
            }else {
                userDetail.setCommunity(userCity.getCommunity());
                userDetail.setArea(userCity.getArea());
                userDetail.setCity(userCity.getCity());
            }
        }
        return new PageInfo<>(userDetails);
    }
}
