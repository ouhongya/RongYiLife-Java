package com.rysh.module.store.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.store.beans.StoreTag;
import com.rysh.module.store.beans.StoreTagDisplay;
import com.rysh.module.store.service.StoreTagService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/server/storetag")
@Api(description = "周边商铺标签API")
public class StoreTagController {

    @Autowired
    private StoreTagService service;

    @ApiOperation(value = "添加商铺标签",notes = "只需要传入name")
    @PostMapping("/add")
    public QueryResponseResult addTag(StoreTag tag){
        int i = service.addTag(tag);
        if (i == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "删除商铺标签",notes = "请睁大你的眼睛 这里是delete请求!!!")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteTag(@PathVariable String id){
        try {
            service.deleteTag(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("当试图删除id={}的周边商铺分类时出错QAQ",id);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("标签回显")
    @GetMapping("info/{id}")
    public QueryResponseResult tagInfo(@PathVariable String id){
        StoreTag tag  = service.info(id);
        QueryResult<StoreTag> result = new QueryResult<>();
        result.setData(tag);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("更新标签")
    @PutMapping("/update")
    public QueryResponseResult updateTag(StoreTag tag){
        try {
            service.updateTag(tag);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("周边商铺更新时出错啦QAQ"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "查看所有的标签信息",notes = "排序字段请传created_time 或者name")
    @GetMapping("/all")
    public QueryResponseResult getAllTagInfo(ParamBean paramBean){
        PageInfo<StoreTagDisplay> tagDisplay = service.displayTag(paramBean);
        QueryResult<StoreTagDisplay> result = new QueryResult<>();
        result.setData(tagDisplay);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }




}
