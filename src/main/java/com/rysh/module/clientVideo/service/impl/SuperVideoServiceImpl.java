package com.rysh.module.clientVideo.service.impl;

import com.rysh.module.classroom.beans.Video;
import com.rysh.module.clientVideo.mapper.SuperVideoMapper;
import com.rysh.module.clientVideo.service.SuperVideoService;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperVideoServiceImpl implements SuperVideoService {

   @Autowired
   private SuperVideoMapper superVideoMapper;

    @Override
    public List<Video> findAllVideo(String uid) throws Exception {
        List<Video> videos=superVideoMapper.findAllVideo();
        //为所有视频设置点赞数
        for (Video video : videos) {
            //查询每个视频的点赞数
            video.setLikedNum(superVideoMapper.findCountVideoUp(video.getId()));
            //查询当前用户点过赞的视频
            String id=superVideoMapper.findUpByUser(video.getId(),uid);
            //设置时间毫秒值
            video.setCreateTimeMills(video.getCreatedTime().getTime());
            if(id!=null){
                video.setIsUp(true);
            }
        }
        return videos;
    }

    @Override
    public Boolean setUp(String videoId, String uid) throws Exception {
        String id = superVideoMapper.findUpByUser(videoId, uid);
        if(id!=null){
            return false;
        }
        superVideoMapper.setUp(GenerateUUID.create(),videoId,uid);
        return true;
    }
}
