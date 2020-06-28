package com.rysh.module.activity.mapper;

import com.github.pagehelper.PageInfo;
import com.rysh.module.activity.beans.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ActivityMapper {
    /**
     * 发布活动
     * @param activity
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 13:34
     */
    int insert(Activity activity);

    /**
     * 删除活动
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 13:55
     */
    int delete(String id);

    /**
     * 更新活动
     * @param activity
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 14:04
     */
    int update(Activity activity);

    /**
     * 查询所有
     * @param search
     * @return java.util.List<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/12 14:28
     */
    List<ActivityVo> findAll(@Param("search") String search,@Param("option") int option,@Param("now") Date now);

    /**
     * 手机用户报名参加活动
     * @param activityUser
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/12 15:50
     */
    int activitySignUp(ActivityUser activityUser);

    /**
     * 根据ID查询活动
     * @param activityId
     * @return com.rysh.module.activity.beans.Activity
     * @author Hsiang Sun
     * @date 2019/10/12 17:54
     */
    Activity findById(String activityId);

    /**
     * 查询所有未通过审核的活动
     * @param
     * @return java.util.List<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/15 9:55
     */
    List<Activity> findUncheck();

    /**
     * 一键审核活动(pass or not)
     * @param ids
	 * @param operation
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/15 10:02
     */
    void check(@Param("ids") List<String> ids,
               @Param("operation") String operation,
               @Param("login") String login,
               @Param("passTime") Date passTime,
               @Param("passComment") String passComment);

    /**
     * 查询审核历史纪录
     * @param search
     * @return java.util.List<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/15 13:39
     */
    List<Activity> findCheckHistory(@Param("search") String search);

    /**
     * 活动批量签到
     * @param ids
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/16 16:03
     */
    void signMany(@Param("ids") List<String> ids);

    /**
     * 通过活动Id 查询未通过确认的参加用户
     * @param id
     * @return java.util.List<com.rysh.module.activity.beans.ActivityUser>
     * @author Hsiang Sun
     * @date 2019/10/16 16:52
     */
    List<JoinActivityUser> findJoinUserByActivityId(String id);

    /**
     * 报名成功之后减少可报名数量
     * @param
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/17 11:42
     */
    void reduceNum(String activityId);

    /**
     * 根据活动开始结束时间统计
     * @param startTime
     * @param endTime
     * @return java.util.List<com.rysh.module.activity.beans.ActivityStatistics>
     * @author Hsiang Sun
     * @date 2019/10/17 14:54
     */
    List<ActivityStatistics> ActivityStatisticsByTime(@Param("startTime") Date startTime,@Param("endTime") Date endTime);

    /**
     * 手机端获取排行榜
     * @param
     * @return java.util.List<com.rysh.module.activity.beans.ClientRank>
     * @author Hsiang Sun
     * @date 2019/10/18 11:26
     */
    List<ClientRank> findClientRank();

    /**
     * 检查当前用户是否已经报名了该活动
     * @param activityId
     * @param login
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/19 10:50
     */
    int isExist(@Param("activityId") String activityId,@Param("userId") String login);

    /**
     * 检查是否已经有用户参加了此次活动
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/19 11:23
     */
    int checkHasUserJoin(String id);

    /**
     * 通过关键词查询活动
     * @param key
     * @return com.github.pagehelper.PageInfo<com.rysh.module.activity.beans.Activity>
     * @author Hsiang Sun
     * @date 2019/10/25 17:08
     */
    List<Activity> findByName(String key);

    /**
     * 添加活动图片
     * @param activityImg
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/7 17:28
     */
    int addActivityImg(ActivityImg activityImg);

    /**
     * 添加活动内容
     * @param activityContent
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/7 17:33
     */
    int addActivityContent(ActivityContent activityContent);

    /**
     * 通过activityID删除活动图片
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/8 10:23
     */
    void deleteActivityImg(String id);

    /**
     * 更新活动图片
     * @param activityImg
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/8 10:24
     */
    void updateActivityImg(ActivityImg activityImg);

    /**
     * 根据activityId删除活动内容
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/8 10:27
     */
    void deleteActivityContent(String id);

    /**
     * 更新活动内容
     * @param activityContent
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/8 10:27
     */
    void updateActivityContent(ActivityContent activityContent);

    /**
     * 通过活动id查询活动图片
     * @param id
     * @return com.rysh.module.activity.beans.ActivityImg
     * @author Hsiang Sun
     * @date 2019/11/8 10:59
     */
    List<ActivityImg> findActivityImgByActivityId(String id);

    /**
     * 通过活动id查询活动内容
     * @param id
     * @return java.util.List<com.rysh.module.activity.beans.ActivityContent>
     * @author Hsiang Sun
     * @date 2019/11/8 11:04
     */
    List<ActivityContent> findActivityContentByActivityId(String id);

    /**
     * 通过活动id回显所有活动信息
     * @param id
     * @return com.rysh.module.activity.beans.ActivityVo
     * @author Hsiang Sun
     * @date 2019/11/8 13:40
     */
    ActivityVo findActivityAllInfoById(String id);

    /**
     * 通过活动id查询参加的人数
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/11/13 16:28
     */
    Integer findJoinedNumByActivityId(String id);
}
