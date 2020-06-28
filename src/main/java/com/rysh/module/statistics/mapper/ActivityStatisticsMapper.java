package com.rysh.module.statistics.mapper;

import com.rysh.module.statistics.beans.ActivityContent;
import com.rysh.module.statistics.beans.ActivityDetail;
import com.rysh.module.statistics.beans.JoinedUser;
import com.rysh.module.statistics.beans.TimeCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ActivityStatisticsMapper {

    /**
     * 查询时间段内举办的活动
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.TimeCount>
     * @author Hsiang Sun
     * @date 2019/11/22 14:37
     */
    List<TimeCount> findActivityByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询一段时间内的活动详情
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.ActivityDetail>
     * @author Hsiang Sun
     * @date 2019/11/22 15:16
     */
    List<ActivityDetail> findActivityDetailByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 通过活动查询活动的描述内容
     * @param id
     * @return java.util.List<com.rysh.module.statistics.beans.ActivityContent>
     * @author Hsiang Sun
     * @date 2019/11/22 16:20
     */
    List<ActivityContent> findActivityContent(String id);

    /**
     * 查看当前活动的参加用户
     * @param id
     * @return java.util.List<com.rysh.module.statistics.beans.JoinedUser>
     * @author Hsiang Sun
     * @date 2019/11/22 17:19
     */
    List<JoinedUser> findActivityJoinUserById(String id);

    /**
     * 通过活动id 查询活动名字
     * @param id
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/11/22 17:45
     */
    String findActivityNameById(String id);
}
