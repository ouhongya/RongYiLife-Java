package com.rysh.module.statistics.mapper;

import com.rysh.module.statistics.beans.ArticleDetail;
import com.rysh.module.statistics.beans.TimeCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleStatisticsMapper {

    /**
     * 文章统计
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.TimeCount>
     * @author Hsiang Sun
     * @date 2019/11/25 9:41
     */
    List<TimeCount> findArticleByTime(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询文章详情
     * @param startTime
	 * @param endTime
     * @return java.util.List<com.rysh.module.statistics.beans.ArticleDetail>
     * @author Hsiang Sun
     * @date 2019/11/25 10:42
     */
    List<ArticleDetail> findArticleDetailByTime(@Param("startTime") String startTime,@Param("endTime") String endTime);

    /**
     * 查询文章喜欢的点赞数
     * @param id
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/11/25 10:52
     */
    Integer findArticleLikedById(String id);
}
