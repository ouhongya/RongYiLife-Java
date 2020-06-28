package com.rysh.module.statistics.mapper;

import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.statistics.beans.TimeCount;
import com.rysh.module.statistics.beans.UserDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StatisticsServiceMapper {
    /**
     * 查询在时间段的用户
     * @param startTime
     * @param endTime
     * @return com.rysh.module.clientLoginRegister.beans.User
     * @author Hsiang Sun
     * @date 2019/11/21 15:54
     */
    List<TimeCount> findUserByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询时间段内注册用户的详细信息
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.UserDetail>
     * @author Hsiang Sun
     * @date 2019/11/22 9:50
     */
    List<UserDetail> findUserDetailByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    UserDetail findUserCity(@Param("id") String id);
}
