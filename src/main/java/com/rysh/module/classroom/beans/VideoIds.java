package com.rysh.module.classroom.beans;

import lombok.Data;

import java.util.List;

/**
 * 一键审核 id集合
 * @author Hsiang Sun
 * @date 2019/11/11 9:59
 */
@Data
public class VideoIds {
    private List<String> ids;
    private String passComment;
}
