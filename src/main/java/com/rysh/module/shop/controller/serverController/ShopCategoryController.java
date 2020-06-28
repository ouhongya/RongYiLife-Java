package com.rysh.module.shop.controller.serverController;

import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.shop.service.ShopCategoryService;
import com.rysh.module.shop.beans.ShopCategory;
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
@RequestMapping("server/shop/category")
@Log4j2
@Api(description = "自营商城商品分类接口")
public class ShopCategoryController implements ServerUserControllerApi {

    @Autowired
    private ShopCategoryService service;

    @ApiOperation("新增自营商城商品分类")
    @PostMapping("/add")
    public QueryResponseResult addCategory(@RequestBody ShopCategory category){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.addCategory(category,login);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 当前可用自营商城分类
     * @param
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/9/4 10:24
     */
    @ApiOperation("查询当前所有可用的自营商城分类")
    @GetMapping("/all")
    public QueryResponseResult getAllCategory(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ShopCategory> categoryList =  service.AvailableCategory(login);
        QueryResult result = new QueryResult();
        result.setData( categoryList );
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    /**
     * 删除自营商城分类
     * @param id
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/9/4 10:56
     */
    @ApiOperation("删除自营商城分类 需要当前分类的id")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteCategory(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.deleteCategory(id,login);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("根据自营商城分类编辑 数据回显")
    @GetMapping("/{id}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id){
        QueryResult<ShopCategory> result = null;
        try {
            ShopCategory category = service.getCategoryById(id);
            result = new QueryResult<>();
            result.setData(category);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("根据自营商城分类的ID 更新名字")
    @PutMapping("/update/{id}/{name}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id,@PathVariable String name){
        int i = service.updateName(id,name);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("当前用户的自营商城分类信息查看")
    @GetMapping("/info")
    public QueryResponseResult categoryRun(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            List<CategoryInfo> info = service.getCategoryInfo(login);
            QueryResult result = new QueryResult();
            result.setData( info );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


}
