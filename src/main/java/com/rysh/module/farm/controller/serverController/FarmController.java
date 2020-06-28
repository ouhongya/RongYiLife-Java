package com.rysh.module.farm.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.farm.beans.Farm;
import com.rysh.module.farm.beans.FarmAlbum;
import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.farm.service.FarmService;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server/farm")
@Log4j2
@Api(description = "农场接口")
public class FarmController implements ServerUserControllerApi {

    @Autowired
    private FarmService farmService;

    @Autowired
    private AccountMapper accountMapper;

    @ApiOperation("新增农场 参数:name,address,areaId,default_sort")
    @PostMapping("/add")
    public QueryResponseResult addFarm(@RequestBody Farm farm){
        int i = farmService.addFarm(farm);
        if (i == 1 ){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
        return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("当前农场的基本信息")
    @GetMapping("/info/{id}")
    public QueryResponseResult farmInfo(@PathVariable String id){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        User loginUser = accountMapper.findUserByUsername(login);
        QueryResult<DisplayInfo> result = new QueryResult<>();
        try {
            DisplayInfo displayInfo = farmService.farmInfo(loginUser.getId(),id);
            result.setData(displayInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }

    }

    @ApiOperation("当前农场基本信息的更新")
    @PutMapping("/info/update")
    public QueryResponseResult updateFarmInfo(@RequestBody FarmAndUser farm){
        try {
            farmService.updateFarmInfo(farm);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新农场基本信息时出错啦QAQ"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("根据农场id获取当前农场信息 用于数据回显")
    @GetMapping("/farm/{id}")
    public QueryResponseResult getFarmById(@PathVariable String id){
        try {
            FarmAndUser farm = farmService.farmById(id);
            QueryResult<Farm> result = new QueryResult<>();
            result.setData(farm);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("农场信息回显失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("农场信息更新")
    @PostMapping("/update")
    public QueryResponseResult updateFarm(@RequestBody Farm farm){
        int i = farmService.updateFarm(farm);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新农场排序大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "需要更新的id"),
            @ApiImplicitParam(name = "value",value = "更新后的大小")
    })
    @PutMapping("/sort/{id}/{value}")
    public QueryResponseResult updateSort(@PathVariable String id,@PathVariable String value){
        int sortValue = Integer.parseInt(value);
        try {
            farmService.updateSort(id,sortValue);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新农场排序大小失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "农场相册banner上传")
    @PostMapping("/album/banner/add/{id}")
    public QueryResponseResult addFarmAlbum(@RequestParam("file") MultipartFile file,@PathVariable String id){
        try {
            FarmAlbum farmAlbum = farmService.addBannerAlbum(file,id);
            QueryResult<FarmAlbum> result = new QueryResult<>();
            result.setData(farmAlbum);
            return new QueryResponseResult( CommonCode.SUCCESS,result);
        } catch (RuntimeException e) {
            log.error("{}添加商铺相册banner失败",new Date());
            return new QueryResponseResult(CommonCode.BEYOND_LIMITNUM);
        }
    }

    @ApiOperation("添加相册cover")
    @PostMapping("/album/cover/add/{id}")
    public QueryResponseResult addCoverAlbum(@RequestParam("file") MultipartFile file,@PathVariable String id){
        try {
            FarmAlbum storeAlbum = farmService.addCoverAlbum(file, id);
            QueryResult<FarmAlbum> result = new QueryResult<>();
            result.setData(storeAlbum);
            return new QueryResponseResult( CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("{}添加农场相册cover失败",new Date());
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除相册")
    @DeleteMapping("/album/delete/{id}")
    public QueryResponseResult deleteAlbum(@PathVariable String id){
        try {
            farmService.deleteAlbum(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (RuntimeException e) {
            log.error("删除相册时失败"+e);
            return new QueryResponseResult(CommonCode.BEYOND_LIMITNUM);
        }
    }

    @ApiOperation(value = "查询当前商铺的相册内容",notes = "内部账号请传id 外部为null")
    @GetMapping("/album/all/{id}")
    public QueryResponseResult getAllStoreAlbum(@PathVariable String id){
        try {
            List<FarmAlbum> storeAlbums =  farmService.allStoreAlbum(id);
            QueryResult<FarmAlbum> result = new QueryResult<>();
            result.setData(storeAlbums);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询当前{}相册失败",id);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("农场相册更新")
    @PutMapping("/album/{id}")
    public QueryResponseResult updateAlbum(@RequestBody List<FarmAlbum> farmAlbums,@PathVariable String id){
        try {
            farmService.updateAlbum(farmAlbums,id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("农场相册更新失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("所有的农场信息 用于首次fetchDate")
    @GetMapping("/all")
    public QueryResponseResult allFarm(ParamBean param){
        return featchDate(param);
    }

    @ApiOperation("删除农场")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deleteFarm(@PathVariable String id){
        int i = farmService.deletFarm(id);
       if (i == 1){
           return new QueryResponseResult(CommonCode.SUCCESS);
       }else {
           return new QueryResponseResult(CommonCode.FAIL);
       }
    }

    @ApiOperation("农场搜索")
    @GetMapping("/findAllFarm")
    public QueryResponseResult searchFarm(ParamBean param){
        return featchDate(param);
    }

    @ApiOperation("农场上市")
    @PutMapping("/apply/{operation}/{id}")
    public QueryResponseResult applyToClient(@PathVariable String id,@PathVariable String operation) {
        int i = farmService.applyToClient(id,operation);
        if (i == 1 ) {
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else if (i == -1){
            return new QueryResponseResult(CommonCode.PARAMETER_ERROR);//未上传封面图
        }else if (i == -2){
            return new QueryResponseResult(CommonCode.TOKEN_ERROR);//未填写简介和手机号
        }
        else {
           return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 农场搜索与查询所有数据
     * @param param
     * @return com.rysh.system.response.QueryResponseResult
     * @author Hsiang Sun
     * @date 2019/9/19 16:50
     */
    private QueryResponseResult featchDate(ParamBean param){
        PageInfo<FarmAndUser> farms = farmService.search(param);
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

}
