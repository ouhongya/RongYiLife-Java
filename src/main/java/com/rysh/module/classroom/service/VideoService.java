package com.rysh.module.classroom.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.Video;
import com.rysh.module.classroom.beans.VideoIds;
import com.rysh.module.classroom.beans.VideoVo;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface VideoService {

    /**
     * 添加视频
     * @param videoVo
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/9 14:51
     */
    void addVideo(VideoVo videoVo);

    /**
     * 查询所有的视频
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/9 14:51
     */
    PageInfo<Video> findAllVideo(ParamBean paramBean);

    /**
     *
     * @param id
     * @return com.rysh.module.classroom.beans.VideoVo
     * @author Hsiang Sun
     * @date 2019/11/9 14:51
     */
    Video findVideoById(String id);

    /**
     * 更新视频
     * @param videoVo
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/9 14:57
     */
    void updateVideo(VideoVo videoVo);

    /**
     * 所有待审核视频
     * @param
     * @return java.util.List<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/11 10:02
     */
    PageInfo<Video> allNeedCheck(ParamBean paramBean);

    /**
     * 某一个待审核视频的详情
     * @param id
     * @return java.util.List<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/11 10:03
     */
    Video getOneUncheck(String id);

    /**
     * 一键审核
     * @param videoIds
	 * @param operation
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/11 10:03
     */
    int checkPass(VideoIds videoIds, String operation);

    /**
     * 审核的历史记录
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.classroom.beans.Video>
     * @author Hsiang Sun
     * @date 2019/11/11 10:04
     */
    PageInfo<Video> checkHistory(ParamBean paramBean);

    /**
     * 删除视频
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/11 11:10
     */
    void deleteVideo(String id);

    /**
     * 视频上下架
     * @param operation
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/11 14:00
     */
    void upOrDownApply(String operation, String id);

    /**
     * 更新视频排序
     * @param id
	 * @param valueInt
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/19 10:27
     */
    void updateSort(String id, int valueInt);
}
