package com.rysh.module.statistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.TimeCount;
import com.rysh.module.statistics.beans.VideoDetail;
import com.rysh.module.statistics.mapper.VideoStatisticsMapper;
import com.rysh.module.statistics.service.VideoStatisticsService;
import com.rysh.module.utils.TimeCale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@Log4j2
public class VideoStatisticsServiceImpl implements VideoStatisticsService {

    @Autowired
    private VideoStatisticsMapper mapper;

    @Override
    public DayUserRig getVideoStatistics(String startTime, String endTimeS) {
        DayUserRig dayUserRig = new DayUserRig();
        String endTime = TimeCale.delayOneDay(endTimeS);

        List<TimeCount> timeList = null;
        try {
            timeList = TimeCale.timeList(startTime, endTimeS);
        } catch (ParseException e) {
            log.error("丁聪的时间工具报错啦！");
        }
        for (TimeCount timeCount : timeList) {
            List<TimeCount> user = mapper.findVideoByTime(startTime, endTime);
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
    public PageInfo<VideoDetail> getVideoStatisticsDetail(ParamBean paramBean, String startTime, String endTimeStr) {
        String endTime = TimeCale.delayOneDay(endTimeStr);
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<VideoDetail> videoDetails = mapper.findVideoDetailByTime(startTime,endTime);
        for (VideoDetail videoDetail : videoDetails) {
            videoDetail.setLiked(mapper.findVideoLikedById(videoDetail.getId()));
        }
        return new PageInfo<>(videoDetails);
    }
}
