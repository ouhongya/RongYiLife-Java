package com.rysh.module.classroom.mapper;

import com.rysh.module.classroom.beans.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {

    /**
     * 添加视频
     * @param video
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/9 14:27
     */
    void addVideo(Video video);

    /**
     * 查询所有的视频
     * @param search
     * @return java.util.List<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/9 14:41
     */
    List<Video> findAllVideo(@Param("search") String search);

    /**
     * 通过视频id查询视频内容
     * @param id
     * @return com.rysh.module.classroom.beans.Video
     * @author Hsiang Sun
     * @date 2019/11/9 14:52
     */
    Video findVideoById(String id);

    /**
     * 更新视频
     * @param video
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/11 9:21
     */
    void updateVideo(Video video);

    /**
     * 查询所有未审核的视频
     * @param
     * @return java.util.List<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/11 10:33
     */
    List<Video> findAllUncheck();

    /**
     * 一键审核通过
     * @param video
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/11 10:50
     */
    int checkPass(@Param("video") Video video,@Param("ids") List<String> ids);

    /**
     * 审核历史记录
     * @param
     * @return java.util.List<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/11 11:03
     */
    List<Video> findCheckHistory(@Param("search") String search);

    /**
     * 根据id删除视频
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/11 11:11
     */
    void deleteVideoById(String id);

    /**
     * 视频的上下架
     * @param operation
	 * @param id
	 * @param operator
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/11 14:03
     */
    void updateStatusById(@Param("operation") String operation,@Param("id") String id,@Param("operator") String operator);

    /**
     * 通过视频id获取点赞数
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/11 16:17
     */
    int findLikeNumById(String id);

    /**
     * 更新视频排序字段
     * @param id
	 * @param valueInt
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/19 10:27
     */
    void updateSort(@Param("id") String id,@Param("value") int valueInt);
}
