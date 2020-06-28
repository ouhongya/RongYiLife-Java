package com.rysh.module.clientVideo.service;

import com.rysh.module.classroom.beans.Video;

import java.util.List;

public interface SuperVideoService {
    List<Video> findAllVideo(String uid) throws Exception;

    Boolean setUp(String videoId, String uid) throws Exception;
}
