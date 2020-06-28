package com.rysh.module.farm.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.Tag;
import com.rysh.module.farm.beans.TagResponse;
import com.rysh.module.farm.service.TagService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/tag")
@Log4j2
@Api(description = "标签接口")
public class TagController implements ServerUserControllerApi {

    @Autowired
    private TagService service;

    @ApiOperation("新增标签 [传入一个标签名：name]")
    @PostMapping("/add/{name}")
    public QueryResponseResult addTag(@PathVariable String name){
        String operator = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.addTag(name,operator);
        if (i == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("添加标签失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有的")
    @GetMapping("/all")
    public QueryResponseResult getAllTagItem(ParamBean paramBean){
        PageInfo<TagResponse> tagEntities = service.allTag(paramBean);
        QueryResult<TagResponse> result = new QueryResult<>();
        result.setData(tagEntities);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("删除标签")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteTag(@PathVariable String id){
        try {
            service.deleteTag(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("删除标签失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新标签")
    @PutMapping("/update")
    public QueryResponseResult updateTag(@RequestBody Tag tag){
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            service.updateTag(tag,name);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新标签失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
