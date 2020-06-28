package com.rysh.module.classroom.service.impl;
import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.*;
import com.rysh.module.classroom.mapper.ArticleMapper;
import com.rysh.module.classroom.service.ArticleService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public int addArticle(ArticleParam articleParam) {

        User user = accountMapper.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        String ID = GenerateUUID.create();

        Article article = new Article();
        article.setId(ID);
        article.setTitle(articleParam.getTitle());
        article.setAuthor(articleParam.getAuthor());
        article.setCreatedTime(new Date());
        article.setCover(articleParam.getCover());
        article.setOperator(user.getTrueName());
        article.setOperator(SecurityContextHolder.getContext().getAuthentication().getName());

        //添加文章主体
        int row1 = mapper.addArticleBody(article);

        List<ArticleTxt> contents = articleParam.getContents();
        int rows = 0;
        for (ArticleTxt content : contents) {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setId(GenerateUUID.create());
            articleContent.setContent(content.getContent());
            articleContent.setType(content.getType());
            articleContent.setQueue(content.getQueue());
            articleContent.setArticleId(ID);
            //添加文章内容
            int row2 = mapper.addArticleContent(articleContent);
            rows += row2;
        }
        return rows + row1;
    }

    @Override
    public int applyToClient(String id,String operation) {
        return mapper.updateStatusById(id,operation);
    }

    @Override
    public List<ClientArticle> AllClientArticle(ParamBean paramBean,String userId) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        //PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<ClientArticle> clientArticle = mapper.findClientArticle();
        List<ArticleUserLike> likedList = mapper.findIsLiked(userId);
        for (ClientArticle article : clientArticle) {
            article.setCreatedTimeMilli(article.getCreatedTime().getTime());
            for (ArticleUserLike articleUserLike : likedList) {
                String articleId = articleUserLike.getArticleId();
                if (article.getId().equals(articleId)){
                    article.setIsLiked(true);//是否已经点赞
                }
            }
            article.setLikeNum(mapper.findLikeNumByArticleId(article.getId()));//每篇文章点赞数
        }
        return clientArticle;
    }

    @Override
    public PageInfo<Article> AllServerArticle(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Article> results = mapper.findServerArticle(paramBean.getSearch());
        for (Article result : results) {
            result.setLiked(mapper.findLikeNumByArticleId(result.getId()));
        }
        return new PageInfo<>(results);
    }

    @Override
    public void deleteArticle(String id) {
        User user = accountMapper.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        mapper.deleteArticle(id,user.getTrueName());
    }

    @Override
    @Transactional
    public void updateArticle(ArticleParam articleParam) {
        Article article = new Article();
        article.setId(articleParam.getId());
        article.setTitle(articleParam.getTitle());
        article.setOperator(accountMapper.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getTrueName());
        article.setCover(articleParam.getCover());
        mapper.updateArticle(article);

        mapper.deleteArticleContentById(articleParam.getId());

        List<ArticleTxt> contents = articleParam.getContents();
        for (ArticleTxt content : contents) {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setId(GenerateUUID.create());
            articleContent.setContent(content.getContent());
            articleContent.setType(content.getType());
            articleContent.setQueue(content.getQueue());
            articleContent.setArticleId(articleParam.getId());
            mapper.addArticleContent(articleContent);
        }
    }

    @Override
    public List<ArticleParam> articleDetail(String id) {
        return mapper.findArticleDetail(id);
    }

    @Override
    public ClientArticleDetail clientArticleDetail(String id,String uid) {
        ClientArticleDetail clientArticleDetail = mapper.findClientArticleDetail(id);
        clientArticleDetail.setCreatedTimeMilli(clientArticleDetail.getCreatedTime().getTime());
        Integer count = mapper.findLikeByArticleId(id, uid);
        if(count!=null && count!=0){
            clientArticleDetail.setIsLiked(true);
        }
        int c = mapper.findLikeNumByArticleId(id);
        clientArticleDetail.setLikeNum(c);

        return clientArticleDetail;
    }

    @Override
    public Boolean likeArticle(String articleId, String uid) {
        //先判断是否已经点赞
        Integer isLiked = mapper.findLikeByArticleId(articleId,uid);
        if (isLiked > 0){
            return false;
        }
        ArticleUserLike like = new ArticleUserLike();
        like.setId(GenerateUUID.create());
        like.setUserId(uid);
        like.setArticleId(articleId);
        mapper.likeArticle(like);
        return true;
    }

    @Override
    public int getArticleLikedNum(String articleId) {
        return mapper.findLikeNumByArticleId(articleId);
    }

    @Override
    public void updateArticleSort(String id, int sort) {
        mapper.updateArticleSort(id,sort);
    }
}
