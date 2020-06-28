package com.rysh.module.garbage.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.garbage.beans.Garbage;
import com.rysh.module.garbage.service.GarbageService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server/garbage")
@Log4j2
@Api(description = "垃圾接口")
public class GarbageController implements ServerUserControllerApi {
    @Autowired
    private GarbageService service;

    @ApiOperation("添加")
    @PostMapping("/add")
    public QueryResponseResult add(@RequestBody Garbage garbage){
        int row = service.add(garbage);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("添加垃圾时错误");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult delete(@PathVariable String id){
        int row = service.deleteGarbage(id);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("删除垃圾时错误");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public QueryResponseResult update(@RequestBody Garbage garbage){
        int row = service.updateGarbage(garbage);
        if (row == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("更新垃圾时错误");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有")
    @GetMapping("/all")
    public QueryResponseResult getAll(ParamBean paramBean,String category,String city){
        PageInfo<Garbage> garbageList = null;
        try {
            garbageList = service.all(paramBean,category,city);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
        QueryResult<Garbage> result = new QueryResult<>();
        result.setData(garbageList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }
}
