package com.rysh.module.grange.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.FarmCheckParam;
import com.rysh.module.grange.beans.GrangeItemAndImgs;
import com.rysh.module.grange.beans.GrangeItemView;
import com.rysh.module.grange.beans.GrangeParam;
import com.rysh.module.grange.service.GrangeItemService;
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
@Log4j2
@RestController
@RequestMapping("/server/grangeitem")

@Api(description = "农庄商品接口")
public class GrangeItemController implements ServerUserControllerApi {

    @Autowired
    private GrangeItemService service;

    @ApiOperation("农庄商品的新增 【商品名:name,描述:description,价格:price,分类id:categoryId,商品图片】")
    @PostMapping("/add")
    public QueryResponseResult addGrangeItem(@RequestBody GrangeParam grangeParam){
        System.out.println(grangeParam.getSpec().getPrice());
        int i = service.addNew(grangeParam);
        if (i == grangeParam.getImgUrls().size() + 2 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("==新增农庄商品时出现错误啦@_@==");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("农庄商品的删除")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult addFarmItem(@PathVariable String id){
        int i = service.deleteById(id);
        if (i == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("==删除农庄商品时出现错误啦@_@==");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }



    @ApiOperation("查询所有的待审核商品")
    @GetMapping("/uncheck")
    public QueryResponseResult allNeddCheck(){
        List<GrangeParam> farmItemList = service.getAllNeedCheck();
        QueryResult result = new QueryResult();
        result.setData(farmItemList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("查询某一个待审核商品")
    @GetMapping("/uncheck/{id}")
    public QueryResponseResult oneUncheck(@PathVariable String id){
        List<GrangeParam> farmItemList = service.getOneUncheck(id);
        QueryResult result = new QueryResult();
        result.setData(farmItemList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }



    @ApiOperation("一键审核 【传入需要审核的商品id】")
    @PutMapping("/check/{operation}")
    public QueryResponseResult checkMany(@RequestBody FarmCheckParam checkParam, @PathVariable String operation){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int i = service.checkPass(checkParam.getIds(),name,operation,checkParam.getComment());
        if ( checkParam.getIds().size() == i){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


    @ApiOperation("农庄品数据显示")
    @GetMapping("/item")
    public QueryResponseResult allItem(ParamBean paramBean,String itemId){
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        PageInfo<GrangeItemView> farms = service.search(paramBean,loginName,itemId);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("农庄审核历史记录")
    @GetMapping("/check/history")
    public QueryResponseResult getCheckHistory(ParamBean paramBean){
        PageInfo<GrangeItemView> farms = service.checkHistory(paramBean);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }



    @ApiOperation("根据Id查询回显数据")
    @GetMapping("/item/{id}")
    public QueryResponseResult allChecked(@PathVariable String id){
        GrangeParam datas = service.getItemById(id);
        QueryResult<GrangeItemAndImgs> result = new QueryResult<>();
        result.setData(datas);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("农庄商品更新")
    @PutMapping("/update")
    public QueryResponseResult updateItem(@RequestBody GrangeParam farmParam ){
        String operator = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            int i = service.updateItem(farmParam,operator);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("==农商商品更新失败@^@==");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("商品的上下架")
    @PutMapping("/shelf/{operation}")
    public QueryResponseResult itemOffShelf(@RequestBody List<String> ids,@PathVariable String operation){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        int i =  service.shelf(ids,name,operation);
        if (ids.size() == i){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }
}
