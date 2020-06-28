package com.rysh.module.classroom.mapper;

import com.rysh.module.classroom.beans.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {

    /**
     * 添加文章主体
     * @param article
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/29 12:01
     */
    int addArticleBody(Article article);

    /**
     * 添加文章内容
     * @param articleContent
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/29 12:02
     */
    int addArticleContent(ArticleContent articleContent);

    /**
     * 根据操作和Id更新状态值
     * @param id
     * @return int
     * @author Hsiang Sun
     * @date 2019/10/29 14:21
     */
    int updateStatusById(@Param("id") String id,@Param("operation") String operation);

    /**
     * 获取状态值为1的文章
     * @param
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/29 14:27
     */
    List<ClientArticle> findClientArticle();

    /**
     * 获取状态值不为-1的值
     * @param
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/29 14:41
     */
    List<Article> findServerArticle(@Param("search") String search);

    /**
     * 根据Id删除文章
     * @param id
	 * @param operator
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/29 14:47
     */
    void deleteArticle(@Param("id") String id, @Param("operator") String operator);

    /**
     * 更新文章主体
     * @param article
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/29 14:52
     */
    void updateArticle(Article article);

    /**
     * 根据文章外键删除文章
     * @param articleId
     * @return void
     * @author Hsiang Sun
     * @date 2019/10/29 15:01
     */
    void deleteArticleContentById(String articleId);

    /**
     * 根据文章id查询详情
     * @param id
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleParam>
     * @author Hsiang Sun
     * @date 2019/10/30 14:10
     */
    List<ArticleParam> findArticleDetail(String id);

    /**
     * 根据Id查询客户端查看的文章详情
     * @param id
     * @return com.rysh.module.classroom.beans.ClientArticleDetail
     * @author Hsiang Sun
     * @date 2019/10/30 15:47
     */
    ClientArticleDetail findClientArticleDetail(String id);

    /**
     * 查询某用户在某文章是否已经点赞
     * @param articleId
	 * @param uid
     * @return java.lang.Integer
     * @author Hsiang Sun
     * @date 2019/11/9 13:21
     */
    Integer findLikeByArticleId(@Param("articleId") String articleId,@Param("uid") String uid);

    /**
     * 文章点赞
     * @param like
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/9 13:23
     */
    void likeArticle(ArticleUserLike like);

    /**
     * 通过文章id查询点赞数
     * @param articleId
     * @return int
     * @author Hsiang Sun
     * @date 2019/11/9 13:35
     */
    int findLikeNumByArticleId(String articleId);

    /**
     * 根据用户id查询他的like列表
     * @param userId
     * @return java.util.List<com.rysh.module.classroom.beans.ArticleUserLike>
     * @author Hsiang Sun
     * @date 2019/11/11 14:56
     */
    List<ArticleUserLike> findIsLiked(String userId);

    /**
     * 根据文章id更新排序
     * @param id
	 * @param sort
     * @return void
     * @author Hsiang Sun
     * @date 2019/11/13 16:54
     */
    void updateArticleSort(@Param("id") String id,@Param("sort") int sort);
}
