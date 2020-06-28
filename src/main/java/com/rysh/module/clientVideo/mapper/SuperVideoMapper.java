package com.rysh.module.clientVideo.mapper;

import com.rysh.module.classroom.beans.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SuperVideoMapper {
    List<Video> findAllVideo();

    Integer findCountVideoUp(String id);

    String findUpByUser(@Param("videoId") String id, @Param("userId") String uid);

    void setUp(@Param("id") String id,@Param("videoId") String videoId, @Param("uid") String uid);
}
