package com.rysh.module.classroom.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.Video;
import com.rysh.module.classroom.beans.VideoIds;
import com.rysh.module.classroom.beans.VideoVo;
import com.rysh.module.classroom.mapper.VideoMapper;
import com.rysh.module.classroom.service.VideoService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.module.webSocket.WebSocket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper mapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    public void addVideo(VideoVo videoVo) {
        Video video = new Video();
        video.setId(GenerateUUID.create());
        video.setTitle(videoVo.getTitle());
        video.setIntros(videoVo.getIntros());
        video.setUrl(videoVo.getUrl());
        video.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        video.setPass(0);
        video.setPassComment(null);
        video.setCreatedTime(new Date());
        video.setStatus(0);//未发布到客户端
        video.setDefaultSort(videoVo.getDefaultSort());
        mapper.addVideo(video);
        //websocket通知前端
        webSocket.sendMsg("0E8C297759CC426F82BED83F7D3574FB","有新的视频来了");
    }

    @Override
    public PageInfo<Video> findAllVideo(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        String search = ToNullUtil.toNull(paramBean.getSearch());
        List<Video> results = mapper.findAllVideo(search);
        for (Video result : results) {
            int likeNum = mapper.findLikeNumById(result.getId());//查询点赞数
            result.setLikedNum(likeNum);
        }
        return new PageInfo<>(results);
    }

    @Override
    public Video findVideoById(String id) {
        return mapper.findVideoById(id);
    }

    @Override
    public void updateVideo(VideoVo videoVo) {
        Video video = new Video();
        video.setId(videoVo.getId());
        video.setTitle(videoVo.getTitle());
        video.setIntros(videoVo.getIntros());
        video.setUrl(videoVo.getUrl());
        video.setPass(0);
        video.setDefaultSort(videoVo.getDefaultSort());
        video.setStatus(0);//需要重新发布
        video.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        mapper.updateVideo(video);
    }

    @Override
    public PageInfo<Video> allNeedCheck(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Video> resultList = mapper.findAllUncheck();
        return new PageInfo<>(resultList);
    }

    @Override
    public Video getOneUncheck(String id) {
        return mapper.findVideoById(id);
    }

    @Override
    public int checkPass(VideoIds videoIds, String operation) {
        Video video = new Video();

        if ("pass".equals(operation)){
            video.setPass(1);
            video.setStatus(1);//审核通过直接上架
        }else if ("fail".equals(operation)){
            video.setPass(-1);
        }else {
            log.error("一键审核失败 operation => {}",operation);
            return 0;
        }

        video.setPassOperator(SecurityContextHolder.getContext().getAuthentication().getName());
        video.setPassComment(videoIds.getPassComment());
        video.setPassTime(new Date());

        List<String> ids = new ArrayList();
        ids = videoIds.getIds();

        int row =  mapper.checkPass(video,ids);
        return row;
    }

    @Override
    public PageInfo<Video> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Video> resultList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<>(resultList);
    }

    @Override
    public void deleteVideo(String id) {
        mapper.deleteVideoById(id);
    }

    @Override
    public void upOrDownApply(String operation, String id) {
        String operator = SecurityContextHolder.getContext().getAuthentication().getName();
        mapper.updateStatusById(operation,id,operator);
    }

    @Override
    public void updateSort(String id, int valueInt) {
        mapper.updateSort(id,valueInt);
    }
}
