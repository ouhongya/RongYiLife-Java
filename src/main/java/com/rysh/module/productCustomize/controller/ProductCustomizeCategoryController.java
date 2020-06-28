package com.rysh.module.productCustomize.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.ProductCustomizeCategory;
import com.rysh.module.productCustomize.service.ProductCustomizeCategoryService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/customizeCategory")
public class ProductCustomizeCategoryController {

    @Autowired
    private ProductCustomizeCategoryService service;

    @ApiOperation("添加社区定制分类名")
    @PostMapping("/add")
    public QueryResponseResult addProductCustomize(String name){
        try {
            service.addCategory(name);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteCategory(@PathVariable String id){
        try {
            service.deleteCategory(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新分类名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "分类的id"),
            @ApiImplicitParam(name = "name",value = "新的分类名"),
    })
    @PutMapping("/update/{id}/{name}")
    public QueryResponseResult updateCategory(@PathVariable String id,@PathVariable String name){
        try {
            service.updateCategory(id,name);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有的分类信息")
    @GetMapping("/all")
    public QueryResponseResult allCategory(ParamBean paramBean){
        try {
            PageInfo<ProductCustomizeCategory> resultInfo = service.getAll(paramBean);
            QueryResult<ProductCustomizeCategory> result = new QueryResult<>();
            result.setData(resultInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

}
