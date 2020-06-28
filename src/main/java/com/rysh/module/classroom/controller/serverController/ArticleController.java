package com.rysh.module.classroom.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.Article;
import com.rysh.module.classroom.beans.ArticleParam;
import com.rysh.module.classroom.service.ArticleService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/article")
@Api(description = "文章接口")
public class ArticleController {

    @Autowired
    private ArticleService service;

    @ApiOperation("添加文章")
    @PostMapping("/add")
    public QueryResponseResult addArticle(@RequestBody ArticleParam articleParam){
        int row = service.addArticle(articleParam);
        if (row == articleParam.getContents().size() + 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "将文章上架到客户端",notes = "参数1：文章上将请传up,下架请传down 参数2:文章id")
    @PutMapping("/apply/{operation}/{id}")
    public QueryResponseResult applyArticleToClient(@PathVariable String id,@PathVariable String operation){
        int row = service.applyToClient(id,operation);
        if (row == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "获取所有文章",notes = "search参数：1.综合排序:default 2.已发布:up 3.未发布:down")
    @GetMapping("/all")
    public QueryResponseResult getAllArticle(ParamBean paramBean){
        PageInfo<Article> resultList = service.AllServerArticle(paramBean);
        QueryResult<Article> result = new QueryResult<>();
        result.setData(resultList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteArticle(@PathVariable String id){
        try {
            service.deleteArticle(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新文章")
    @PutMapping("/update")
    public QueryResponseResult updateArticle(@RequestBody ArticleParam articleParam){
        try {
            service.updateArticle(articleParam);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("修改文章排序字段")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "活动id"),
            @ApiImplicitParam(name = "sort",value = "排序大小",type = "int")
    })
    @PutMapping("/queue/{id}/{sort}")
    public QueryResponseResult updateQueue(@PathVariable String id,@PathVariable String sort){
        try {
            int intSort = Integer.parseInt(sort);
            service.updateArticleSort(id,intSort);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查看文章详情")
    @GetMapping("/detail/{id}")
    public QueryResponseResult ArticleDetail(@PathVariable String id){
        List<ArticleParam> resultList = service.articleDetail(id);
        QueryResult<ArticleParam> result = new QueryResult<>();
        result.setData(resultList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }




}
