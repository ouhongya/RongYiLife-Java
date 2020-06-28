package com.rysh.module.classroom.service;


import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.Article;
import com.rysh.module.classroom.beans.ArticleParam;
import com.rysh.module.classroom.beans.ClientArticle;
import com.rysh.module.classroom.beans.ClientArticleDetail;
import com.rysh.module.commonService.beans.ParamBean;

import java.util.List;

public interface ArticleService {

    /**
     * 添加文章
     * @param articleParam
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/29 11:37
     */
    int addArticle(ArticleParam articleParam);

    /**
     * 将文章上/下架到客户端
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/29 14:18
     */
    int applyToClient(String id,String operation);

    /**
     * 手机端获取所有文章
     * @param paramBean
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/29 14:26
     */
    List<ClientArticle> AllClientArticle(ParamBean paramBean,String userId);

    /**
     * 服务端获取所有文章
     * @param paramBean
     * @return com.github.pagehelper.PageInfo<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/29 14:39
     */
    PageInfo<Article> AllServerArticle(ParamBean paramBean);

    /**
     * 根据Id删除文章
     * @param id
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/29 14:45
     */
    void deleteArticle(String id);

    /**
     * 更新文章
     * @param articleParam
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/29 15:03
     */
    void updateArticle(ArticleParam articleParam);

    /**
     * 查看文章详情
     * @param id
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/30 14:10
     */
    List<ArticleParam> articleDetail(String id);

    /**
     * 客户端根据id查看文章详情
     * @param id
     * @return com.rysh.module.classroom.beans.ClientArticleDetail
     * @author Hsiang Sun
     * @date 2019/10/30 15:46
     */
    ClientArticleDetail clientArticleDetail(String id,String uid);
    
    /**
     * 文章点赞
     * @param articleId
	 * @param uid 
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/9 13:14
     */
    Boolean likeArticle(String articleId, String uid);

    /**
     * 根据文章id获取文章点赞数
     * @param articleId
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/9 13:34
     */
    int getArticleLikedNum(String articleId);

    /**
     * 更新文章排序字段
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/13 16:53
     */
    void updateArticleSort(String id, int sort);
}
