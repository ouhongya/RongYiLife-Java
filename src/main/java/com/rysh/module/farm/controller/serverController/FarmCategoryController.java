package com.rysh.module.farm.controller.serverController;

import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.farm.beans.CategoryInfo;
import com.rysh.module.farm.beans.FarmCategory;
import com.rysh.module.farm.service.FarmCategoryService;
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
@RequestMapping("server/farm/category")
@Log4j2
@Api(description = "农场商品分类接口")
public class FarmCategoryController implements ServerUserControllerApi {

    @Autowired
    private FarmCategoryService service;

    @ApiOperation("新增农场商品分类")
    @PostMapping("/add/{id}")
    public QueryResponseResult addCategory(@RequestBody FarmCategory category,@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.addCategory(category,login,id);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else if (i == -1){
            return new QueryResponseResult(CommonCode.UNAUTHENTIC);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 当前可用农场分类
     * @param
     * @return com.rysh.system.response.QueryResponseResult
     * @author HsiangSun
     * @date 2019/9/4 10:24
     */
    @ApiOperation("查询当前所有可用的农场分类")
    @GetMapping("/all/{id}")
    public QueryResponseResult getAllCategory(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        List<FarmCategory> categoryList =  service.AvailableCategory(login,id);
        QueryResult result = new QueryResult();
        result.setData( categoryList );
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

   /**
    * 删除农场分类
    * @param itemId
	 * @param categoryId
    * @return com.rysh.system.response.QueryResponseResult
    * @author Hsiang Sun
    * @date 2019/10/25 11:06
    */
    @ApiOperation("删除农场分类 需要当前分类的id")
    @DeleteMapping("/delete/{itemId}/{categoryId}")
    public QueryResponseResult deleteCategory(@PathVariable String itemId,@PathVariable String categoryId){
        int i = service.deleteCategory(itemId,categoryId);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else if (i == -1){
            return new QueryResponseResult(CommonCode.UNAUTHENTIC);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("根据农场分类编辑 数据回显")
    @GetMapping("/{categoryId}")
    public QueryResponseResult updateCategoryName(@PathVariable String categoryId){
        QueryResult<FarmCategory> result = null;
        try {
            FarmCategory category = service.getCategoryById(categoryId);
            result = new QueryResult<>();
            result.setData(category);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("根据农场分类的ID 更新名字")
    @PutMapping("/update/{id}/{name}")
    public QueryResponseResult updateCategoryName(@PathVariable String id,@PathVariable String name){
        int i = service.updateName(id,name);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("当前用户的农庄分类信息查看")
    @GetMapping("/info/{id}")
    public QueryResponseResult categoryRun(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
       // Integer type = TokenUtils.getIsInsiderByToken();
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
