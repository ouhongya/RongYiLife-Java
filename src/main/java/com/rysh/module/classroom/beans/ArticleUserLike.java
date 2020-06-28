package com.rysh.module.classroom.beans;

import lombok.Data;

/*
*
* 用户-文章 点赞中间表
* */
@Data
public class ArticleUserLike {
    private String id;
    private String userId;
    private String articleId;
}
