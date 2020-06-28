package com.rysh.module.statistics.mapper;

import com.rysh.module.statistics.beans.TimeCount;
import com.rysh.module.statistics.beans.VideoDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoStatisticsMapper {

    /**
     * 视频统计
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.TimeCount>
     * @author Hsiang Sun
     * @date 2019/11/25 11:55
     */
    List<TimeCount> findVideoByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查看视频统计详情
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.VideoDetail>
     * @author Hsiang Sun
     * @date 2019/11/25 14:56
     */
    List<VideoDetail> findVideoDetailByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询视频的点赞数
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/11/25 14:59
     */
    Integer findVideoLikedById(String id);
}
