package com.rysh.module.statistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.*;
import com.rysh.module.statistics.mapper.ActivityStatisticsMapper;
import com.rysh.module.statistics.service.ActivityStatisticsService;
import com.rysh.module.utils.TimeCale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
@Log4j2
@Service
public class ActivityStatisticsServiceImpl implements ActivityStatisticsService {

    @Autowired
    private ActivityStatisticsMapper mapper;

    @Override
    public DayUserRig getActivityStatistics(String startTime, String endTimeS) {
        DayUserRig dayUserRig = new DayUserRig();
        String endTime = TimeCale.delayOneDay(endTimeS);

        List<TimeCount> timeList = null;
        try {
            timeList = TimeCale.timeList(startTime, endTimeS);
        } catch (ParseException e) {
            log.error("丁聪的时间工具报错啦！");
        }
        for (TimeCount timeCount : timeList) {
            List<TimeCount> user = mapper.findActivityByTime(startTime, endTime);
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
    public PageInfo<ActivityDetail> getActivityStatisticsDetail(ParamBean paramBean, String startTime, String endTimeStr) {

        String endTime = TimeCale.delayOneDay(endTimeStr);
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy("a."+paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());

        List<ActivityDetail> activityDetails = mapper.findActivityDetailByTime(startTime,endTime);
        for (ActivityDetail activityDetail : activityDetails) {
            activityDetail.setDescription(getDescription(activityDetail.getId()));
        }
        return new PageInfo<>(activityDetails);
    }

    /**
     * 根据活动id 生成活动描述
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/22 16:00
     */
    private String getDescription(String id) {
         List<ActivityContent> contents = mapper.findActivityContent(id);
         if (contents.size() == 0){
             return null;
         }

        Collections.sort(contents,(o1,o2)-> o1.getQueue() - o2.getQueue());//内容排序

        StringBuilder sb = new StringBuilder();
        for (ActivityContent content : contents) {
            sb.append(content.getContent());
        }
        return sb.toString();
    }

    @Override
    public PageInfo<JoinedUser> getActivityJoinUser(String id,ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String activityName = mapper.findActivityNameById(id);
        List<JoinedUser> joins = mapper.findActivityJoinUserById(id);
        for (JoinedUser join : joins) {
            join.setActivityName(activityName);
        }
        return new PageInfo<>(joins);
    }
}
