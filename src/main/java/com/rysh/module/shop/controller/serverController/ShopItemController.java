package com.rysh.module.shop.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.FarmCheckParam;
import com.rysh.module.grange.beans.GrangeItemAndImgs;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.shop.beans.ShopItemView;
import com.rysh.module.shop.beans.ShopParam;
import com.rysh.module.shop.service.ShopItemService;
import com.rysh.module.store.beans.StoreItemView;
import com.rysh.system.UserType;
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
@RequestMapping("/server/shopitem")

@Api(description = "自营商城商品接口")
public class ShopItemController implements ServerUserControllerApi {

    @Autowired
    private ShopItemService service;

    @Autowired
    private AccountMapper accountMapper;

    @ApiOperation("自营商城商品的新增 【商品名:name,描述:description,价格:price,分类id:categoryId,商品图片】")
    @PostMapping("/add")
    public QueryResponseResult addGrangeItem(@RequestBody ShopParam storeParam){

        int i = service.addNew(storeParam);
        if (i == storeParam.getImgUrls().size() + 2 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("==新增自营商城商品时出现错误啦@_@==");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("自营商城商品的删除")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult addFarmItem(@PathVariable String id){
        int i = service.deleteById(id);
        if (i == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            log.error("==删除自营商城商品时出现错误啦@_@==");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }



    @ApiOperation("查询所有的待审核商品")
    @GetMapping("/uncheck")
    public QueryResponseResult allNeedCheck(){
        List<ShopParam> farmItemList = service.getAllNeedCheck();
        QueryResult result = new QueryResult();
        result.setData(farmItemList);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("查询某一个待审核商品")
    @GetMapping("/uncheck/{id}")
    public QueryResponseResult oneUncheck(@PathVariable String id){
        List<ShopParam> farmItemList = service.getOneUncheck(id);
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

    @ApiOperation("自营商城品数据显示")
    @GetMapping("/item")
    public QueryResponseResult allItem(ParamBean paramBean,String category){
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = accountMapper.findUserByUsername(loginName);
        Integer isInsider = user.getIsInsider();
        if (isInsider != UserType.内部账户.typeCode()){
            return new QueryResponseResult(CommonCode.UNAUTHENTIC);
        }
        PageInfo<ShopItemView> farms = service.search(paramBean,category);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("自营商城商品排序")
    @PutMapping("/sort/{id}/{value}")
    public QueryResponseResult updateSort(@PathVariable String id,@PathVariable String value){
        try {
            int sort = Integer.parseInt(value);
            service.updateSort(id,sort);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("自营商城审核历史记录")
    @GetMapping("/check/history")
    public QueryResponseResult getCheckHistory(ParamBean paramBean){
        PageInfo<ShopItemView> farms = service.checkHistory(paramBean);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }



    @ApiOperation("根据Id查询回显数据")
    @GetMapping("/item/{id}")
    public QueryResponseResult allChecked(@PathVariable String id){
        ShopParam data = service.getItemById(id);
        QueryResult<GrangeItemAndImgs> result = new QueryResult<>();
        result.setData(data);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("自营商城商品更新")
    @PutMapping("/update")
    public QueryResponseResult updateItem(@RequestBody ShopParam farmParam ){
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
