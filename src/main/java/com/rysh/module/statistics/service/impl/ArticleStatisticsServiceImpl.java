package com.rysh.module.statistics.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.statistics.beans.ArticleDetail;
import com.rysh.module.statistics.beans.DayUserRig;
import com.rysh.module.statistics.beans.TimeCount;
import com.rysh.module.statistics.mapper.ArticleStatisticsMapper;
import com.rysh.module.statistics.service.ArticleStatisticsService;
import com.rysh.module.utils.TimeCale;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@Log4j2
public class ArticleStatisticsServiceImpl implements ArticleStatisticsService {

    @Autowired
    private ArticleStatisticsMapper mapper;

    @Override
    public DayUserRig articleStatistics(String startTime, String endTimeStr) {
        DayUserRig dayUserRig = new DayUserRig();
        String endTime = TimeCale.delayOneDay(endTimeStr);

        List<TimeCount> timeList = null;
        try {
            timeList = TimeCale.timeList(startTime, endTimeStr);
        } catch (ParseException e) {
            log.error("丁聪的时间工具报错啦！");
        }

        for (TimeCount timeCount : timeList) {
            List<TimeCount> user = mapper.findArticleByTime(startTime, endTime);
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
    public PageInfo<ArticleDetail> getArticleStatisticsDetail(ParamBean paramBean, String startTime, String endTimeStr) {

        String endTime = TimeCale.delayOneDay(endTimeStr);
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<ArticleDetail> articles = mapper.findArticleDetailByTime(startTime,endTime);
        for (ArticleDetail article : articles) {
            article.setLiked(mapper.findArticleLikedById(article.getId()));
        }
        return new PageInfo<>(articles);
    }
}
