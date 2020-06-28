package com.rysh.module.activity.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.activity.beans.*;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.Date;
import java.util.List;

public interface ActivityService {

    /**
     * 添加活动
     * @param activity
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 11:20
     */
    int addActivity(ActivityVo activity);

    /**
     * 删除该活动
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 13:54
     */
    int delete(String id);

    /**
     * 活动更新
     * @param activityVo
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 14:01
     */
    void update(ActivityVo activityVo);

    /**
     * 查询所有活动
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/12 14:21
     */
    PageInfo<ActivityVo> all(ParamBean paramBean,int option);

    /**
     * 手机用户报名参加活动
     * @param activityUser
     * @param userId
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 15:45
     */
    int userSignUp(ActivityUser activityUser, String userId);

    /**
     * 查询所有未通过审核的活动
     * @param
     * @return java.util.List<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/15 9:54
     */
    PageInfo<Activity> uncheckActivity(ParamBean paramBean);

    /**
     * 一键审核活动
     * @param ids
	 * @param operation
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/15 10:00
     */
    void checkActivity(List<String> ids, String operation,String login,String passComment);

    /**
     * 审核历史纪录
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/15 13:37
     */
    PageInfo<Activity> checkHistory(ParamBean paramBean);

    /**
     * 活动批量签到
     * @param ids
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/16 16:02
     */
    void signMany(List<String> ids);

    /**
     * 根据活动id查询参加情况
     * @param paramBean
     * @param id
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.ActivityUser>
     * @author Hsiang Sun
     * @date 2019/10/16 16:49
     */
    PageInfo<JoinActivityUser> getUserByActivityId(ParamBean paramBean, String id);


    /**
     * 活动导出到Excel
     * @param id
     * @return java.util.List<com.rysh.module.activity.beans.JoinActivityUser>
     * @author Hsiang Sun
     * @date 2019/11/20 16:31
     */
    List<JoinActivityUser> getUserByActivityId(String id);

    /**
     * 统计活动参加情况
     * @param timeParam
	 * @param startTime
	 * @param endTime
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.ActivityStatistics>
     * @author Hsiang Sun
     * @date 2019/10/17 14:50
     */
    PageInfo<ActivityStatistics> ActivityStatistics(TimeParam timeParam, Date startTime, Date endTime);

    /**
     * 客户端获取家庭之星排行榜
     * @param
     * @return java.util.List<com.rysh.module.activity.beans.ClientRank>
     * @author Hsiang Sun
     * @date 2019/10/18 11:25
     */
    ClientRankResponse clientFamilyRank();

    /**
     * 活动搜索
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/25 17:07
     */
    PageInfo<Activity> searchActivity(ParamBean paramBean);

    /**
     * 通过活动id回显活动所有信息
     * @param id
     * @return com.rysh.module.activity.beans.ActivityVo
     * @author Hsiang Sun
     * @date 2019/11/8 13:39
     */
    ActivityVo getActivityVoById(String id);
}
