package com.rysh.module.shop.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.shop.beans.Shop;
import com.rysh.module.shop.service.ShopService;
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
@RequestMapping("/server/shop")
@Log4j2
@Api(description = "自营商城API")
public class ShopController implements ServerUserControllerApi {

    @Autowired
    private ShopService service;


    @ApiOperation("新增自营商城 传入name和address")
    @PostMapping("/add")
    public QueryResponseResult addGrange(@RequestBody Shop grange){
        int i = service.addNewShop(grange);
        if ( i != 1){
            return new QueryResponseResult(CommonCode.FAIL);
        }else {
            return new QueryResponseResult(CommonCode.SUCCESS);
        }
    }

    @ApiOperation("所有的自营商城信息 用于首次fetchDate")
    @GetMapping("/all")
    public QueryResponseResult allFarm(ParamBean param){
        return featchDate(param);
    }

    @ApiOperation("根据自营商城id获取当前农场信息 用于数据回显")
    @GetMapping("/grange/{id}")
    public QueryResponseResult getFarmById(@PathVariable String id){
        Shop grange = service.shopById(id);
        if (grange == null){
            return new QueryResponseResult(CommonCode.FAIL);
        }
        QueryResult<Shop> result = new QueryResult<>();
        result.setData(grange);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("当前商铺的基本信息")
    @GetMapping("/info")
    public QueryResponseResult farmInfo(){
        try {
            QueryResult<DisplayInfo> result = new QueryResult<>();
            String login = SecurityContextHolder.getContext().getAuthentication().getName();
            DisplayInfo displayInfo = service.grangeInfo(login);
            result.setData(displayInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("自营商城信息更新")
    @PostMapping ("/update")
    public QueryResponseResult updateFarm(@RequestBody Shop grange){
        int i = service.updateShop(grange);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除自营商城")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deletFarm(@PathVariable String id){
        int i = service.deleteShop(id);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("商铺搜索")
    @GetMapping("/findAllStore")
    public QueryResponseResult searchFarm(ParamBean param){
        return featchDate(param);
    }



    /**
     * 查询所有数据与商铺搜索
     * @param param
     * @return com.rysh.system.response.QueryResponseResult
     * @author Hsiang Sun
     * @date 2019/9/19 16:58
     */
    private QueryResponseResult featchDate(ParamBean param){
        PageInfo<Shop> farms = service.search(param);//调用同一个方法
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }


}
