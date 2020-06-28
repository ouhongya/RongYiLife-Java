package com.rysh.module.grange.controller.serverController;

import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.grange.beans.GrangeCategory;
import com.rysh.module.grange.service.GrangeCategoryService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("server/grange/category")
@Api(description = "农庄商品分类接口")
public class GrangeCategoryController implements ServerUserControllerApi {

    @Autowired
    private GrangeCategoryService service;

    @ApiOperation("新增农庄商品分类")
    @PostMapping("/add/{itemId}")
    public QueryResponseResult addCategory(@RequestBody GrangeCategory category,@PathVariable String itemId){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.addCategory(category,login,itemId);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有的待审核的农庄商品分类 请检查date[]是否为空")
    @GetMapping("/uncheck")
    public QueryResponseResult needCheckCategory(){
        List<GrangeCategory> categories = service.allNeedCheck();
        QueryResult result = new QueryResult();
        result.setData( categories );
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("查询当前所有可用的农场分类")
    @GetMapping("/all/{id}")
    public QueryResponseResult getAllCategory(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<GrangeCategory> categoryList =  service.AvailableCategory(login,id);
        QueryResult result = new QueryResult();
        result.setData( categoryList );
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("删除农场分类 需要当前分类的id")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteCategory(@PathVariable String id){
        int i = service.deleteCategory(id);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("根据农场分类的ID 更新名字")
    @PutMapping("/update/{id}/{name}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id,@PathVariable String name){
        int i = service.updateName(id,name);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("根据农场分类编辑 数据回显")
    @GetMapping("/{id}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id){
        QueryResult<GrangeCategory> result = null;
        try {
            GrangeCategory category = service.getCategoryById(id);
            result = new QueryResult<>();
            result.setData(category);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("批量审核通过")
    @PutMapping("/check")
    public QueryResponseResult checkPass(@RequestBody List<String> ids){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.checkPassMany(name,ids);
        if ( ids.size() == i ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("当前用户的农庄分类信息查看")
    @GetMapping("/info/{id}")
    public QueryResponseResult categoryRun(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<CategoryInfo> info = service.getCategoryInfo(login,id);
            QueryResult result = new QueryResult();
            result.setData( info );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


}
