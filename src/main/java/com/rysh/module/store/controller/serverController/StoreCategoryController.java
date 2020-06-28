package com.rysh.module.store.controller.serverController;

import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.store.beans.StoreCategory;
import com.rysh.module.store.service.StoreCategoryService;
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
@RequestMapping("server/store/category")
@Log4j2
@Api(description = "商铺商品分类接口")
public class StoreCategoryController implements ServerUserControllerApi {

    @Autowired
    private StoreCategoryService service;

    @ApiOperation("新增商铺商品分类")
    @PostMapping("/add")
    public QueryResponseResult addCategory(@RequestBody StoreCategory category){
        int i = service.addCategory(category);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 当前可用商铺分类
     * @param
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/9/4 10:24
     */
    @ApiOperation(value = "查询当前所有可用的商铺分类",notes = "查询当前商铺的分类信息 内部账户请传id 商铺账户请传null ")
    @GetMapping("/all/{id}")
    public QueryResponseResult getAllCategory(@PathVariable String id){
        List<StoreCategory> categoryList =  service.AvailableCategory(id);
        QueryResult result = new QueryResult();
        result.setData( categoryList );
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    /**
     * 删除商铺分类
     * @param id
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/9/4 10:56
     */
    @ApiOperation("删除商铺分类 需要当前分类的id")
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

    @ApiOperation("根据商铺分类编辑 数据回显")
    @GetMapping("/{id}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id){
        QueryResult<StoreCategory> result = null;
        try {
            StoreCategory category = service.getCategoryById(id);
            result = new QueryResult<>();
            result.setData(category);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("根据商铺分类的ID 更新名字")
    @PutMapping("/update/{id}/{name}")
    public QueryResponseResult updateCateegoryName(@PathVariable String id,@PathVariable String name){
        int i = service.updateName(id,name);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "当前用户的农庄分类信息查看",notes = "查询当前商铺的分类信息 内部账户请传id 商铺账户请传null ")
    @GetMapping("/info/{id}")
    public QueryResponseResult categoryRun(@PathVariable String id){
        try {
            List<CategoryInfo> info = service.getCategoryInfo(id);
            QueryResult result = new QueryResult();
            result.setData( info );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


}
