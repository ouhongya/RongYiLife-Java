package com.rysh.module.garbage.controller.serverController;

import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.garbage.beans.ResponseCategory;
import com.rysh.module.garbage.service.GarbageCategoryService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/server/garbageCategory")
@Log4j2
@Api(description = "垃圾类别接口")
public class GarbageCategoryController implements ServerUserControllerApi {

    @Autowired
    private GarbageCategoryService categoryService;

    @Autowired
    private ImageUploadService imageUploadService;

    @ApiOperation("垃圾分类类别添加")
    @PostMapping("/add")
    public QueryResponseResult addCategory(@RequestBody GarbageCategory category){
        try {
            categoryService.add(category);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (RuntimeException e) {
            log.error("添加垃圾类别失败=》"+e);
            return new QueryResponseResult(CommonCode.DOUBLE_CONTENT);
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteCategory(@PathVariable String id){
        try {
            categoryService.delete(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("删除垃圾类别失败=》"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public QueryResponseResult updateCategory(@RequestBody GarbageCategory category){
        try {
            categoryService.update(category);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新垃圾类别失败=》"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("上传垃圾分类图片")
    @PostMapping("/upload")
    public QueryResponseResult uploadImg(@RequestParam("file")MultipartFile file){
        String imgUrl = imageUploadService.upload(file);
        if (imgUrl == null){
            return new QueryResponseResult(CommonCode.UPLOAD_ERROR);
        }else {
            QueryResult<String> result = new QueryResult<>();
            result.setData(imgUrl);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }
    }

    @ApiOperation("查询所有类别")
    @GetMapping("/all/{id}")
    public QueryResponseResult allCategory(@PathVariable String id){
        try {
            List<ResponseCategory> all = categoryService.allAvailable(id);
            QueryResult<GarbageCategory> result = new QueryResult<>();
            result.setData(all);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("更新垃圾类别失败=》"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
