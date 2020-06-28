package com.rysh.module.classroom.controller.clientController;

import com.rysh.module.classroom.beans.ClientArticle;
import com.rysh.module.classroom.beans.ClientArticleDetail;
import com.rysh.module.classroom.service.ArticleService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/client/article")
@Api(description = "客户端文章接口")
public class ClientArticleController {

    @Autowired
    private ArticleService service;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    @ApiOperation(value = "获取文章列表",response = ClientArticle.class,notes = "排序字段建议使用 lasted_update_time;顺序使用 desc ;search 搜索不用传")
    @PostMapping("/all")
    public QueryResponseResult getAllArticle(ParamBean paramBean,String token){

        String userId = checkRedisTokenUtils.checkRedisToken(token);
        if ("".equals(userId) || userId == null){
            return new QueryResponseResult(CommonCode.TOKEN_ERROR);
        }
        List<ClientArticle> resultList = service.AllClientArticle(paramBean,userId);
        QueryResult<ClientArticle> result = new QueryResult<>();
        result.setData(resultList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation(value = "获取文章详情",response = ClientArticleDetail.class)
    @PostMapping("/detail")
    @ApiImplicitParam(name = "id",value = "文章id")
    public QueryResponseResult ClientArticleDetail(String id,String token){
        String uid = checkRedisTokenUtils.checkRedisToken(token);
        if (uid != null) {
            ClientArticleDetail detail = service.clientArticleDetail(id,uid);
            QueryResult<ClientArticleDetail> result = new QueryResult<>();
            result.setData(detail);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }else {
            return new QueryResponseResult(CommonCode.TOKEN_ERROR);
        }

    }

    @ApiOperation(value = "文章点赞")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId",value = "文章id"),
            @ApiImplicitParam(name = "token",value = "token")
    })
    @PostMapping("/like")
    public QueryResponseResult likeArticle(String articleId,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                if(service.likeArticle(articleId, uid)){
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }else {
                    return new QueryResponseResult(CommonCode.FAIL);
                }
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }

        } catch (RuntimeException e) {
           return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation("文章获取点赞数")
    @PostMapping("/liked")
    @ApiImplicitParam(name = "articleId",value = "文章id")
    public QueryResponseResult getArticleLikedNum(String articleId){
        try {
            int likedNum = service.getArticleLikedNum(articleId);
            QueryResult<Integer> result = new QueryResult<>();
            result.setData(likedNum);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
