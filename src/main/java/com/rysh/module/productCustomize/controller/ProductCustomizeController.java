package com.rysh.module.productCustomize.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.productCustomize.beans.*;
import com.rysh.module.productCustomize.service.ProductCustomizeService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/customize")
public class ProductCustomizeController {

    @Autowired
    private ProductCustomizeService service;

    @ApiOperation(value = "添加社群定制")
    @PostMapping("/add")
    public QueryResponseResult add(@RequestBody ProductCustomizeVo productCustomizeVo){
        try {
            service.add(productCustomizeVo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "获取所有的信息",response = ProductCustomizeRo.class)
    @GetMapping("/all")
    public QueryResponseResult all(ParamBean paramBean){
        try {
            PageInfo<ProductCustomizeRo> responseList = service.all(paramBean);
            QueryResult<ProductCustomizeRo> result = new QueryResult<>();
            result.setData(responseList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("社群定制上下架")
    @PutMapping("/apply/{id}/{operation}")
    public QueryResponseResult apply(@PathVariable String id,@PathVariable String operation){
        try {
            service.applyToClient(id,operation);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e){
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult delete(@PathVariable String id){
        try {
            service.delete(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("回显")
    @GetMapping("/detail/{id}")
    public QueryResponseResult detail(@PathVariable String id){
        try {
           ProductCustomizeRo response =  service.detail(id);
            QueryResult<ProductCustomizeRo> result = new QueryResult<>();
            result.setData(response);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public QueryResponseResult update(@RequestBody ProductCustomizeVo productCustomizeVo){
        try {
            service.update(productCustomizeVo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("修改排序大小")
    @PutMapping("/queue/{id}/{sort}")
    public QueryResponseResult updateQueue(@PathVariable String id,@PathVariable String sort){
        try {
            service.updateSort(id,sort);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
        return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查看留言人数")
    @GetMapping("/contact")
    public QueryResponseResult contact(ParamBean paramBean,String id) {
        try {
            PageInfo<ProductCustomizeContact> response = service.findContact(paramBean,id);
            QueryResult<PageInfo<ProductCustomizeContact>> result = new QueryResult<>();
            result.setData(response);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有未通过审核")
    @GetMapping("/uncheck")
    public QueryResponseResult uncheck(ParamBean paramBean){
        try {
            PageInfo<ProductCustomizeVo> activityList = service.uncheck(paramBean);
            QueryResult<ProductCustomizeVo> result = new QueryResult<>();
            result.setData(activityList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("历史记录")
    @GetMapping("/check/history")
    public QueryResponseResult getCheckHistory(ParamBean paramBean){
        PageInfo<ProductCustomize> farms = service.checkHistory(paramBean);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("一键审核通过")
    @PostMapping("/check")
    public QueryResponseResult check(@RequestBody CheckParam checkParam){
        try {
            service.checkPass(checkParam);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }




}
