package com.rysh.module.store.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.store.beans.Store;
import com.rysh.module.store.beans.StoreAlbum;
import com.rysh.module.store.beans.StoreInfo;
import com.rysh.module.store.service.StoreService2;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/server/store")
@Log4j2
@Api(description = "商铺API")
public class StoreController implements ServerUserControllerApi {

    @Autowired
    private StoreService2 service;

    @Autowired
    private ImageUploadService imageUploadService;

    @ApiOperation(value = "当前商铺的基本信息",notes = "/info/这里需要拼接商铺id 如果是外部账户请传null")
    @GetMapping("/info/{id}")
    public QueryResponseResult farmInfo(@PathVariable String id){
        try {
            QueryResult<StoreInfo> result = new QueryResult<>();
            StoreInfo storeInfo = service.grangeInfo(id);
            result.setData(storeInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error(e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有商铺与商铺搜索")
    @GetMapping("/all")
    public QueryResponseResult searchFarm(ParamBean param){
        return featchDate(param);
    }

    @ApiOperation("根据商铺id获取当前商铺信息 用于数据回显")
    @GetMapping("/store/{id}")
    public QueryResponseResult getFarmById(@PathVariable String id){
        try {
            StoreInfo  farm = service.storeById(id);
            QueryResult<StoreInfo> result = new QueryResult<>();
            result.setData(farm);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("周边商铺信息回显失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("商铺信息更新")
    @PutMapping("/update")
    public QueryResponseResult updateStoreInfo(@RequestBody StoreInfo storeInfo){
        try {
            service.updateStoreInfo(storeInfo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("周边商铺更新失败"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation(value = "商铺上/下架",notes = "上架：up 下架：down")
    @PutMapping("/apply/{operation}/{id}")
    public QueryResponseResult storeApplyToClient(@PathVariable String operation,@PathVariable String id){
        try {
            service.applyToClient(operation,id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("商铺上下架错误");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("添加相册banner")
    @PostMapping("/album/banner/add/{id}")
    public QueryResponseResult addBannerAlbum(@RequestParam("file") MultipartFile file,@PathVariable String id){
        try {
            StoreAlbum storeAlbum = service.addBannerAlbum(file,id);
            QueryResult<StoreAlbum> result = new QueryResult<>();
            result.setData(storeAlbum);
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
            StoreAlbum storeAlbum = service.addCoverAlbum(file, id);
            QueryResult<StoreAlbum> result = new QueryResult<>();
            result.setData(storeAlbum);
            return new QueryResponseResult( CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("{}添加商铺相册cover失败",new Date());
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除相册")
    @DeleteMapping("/album/delete/{id}")
    public QueryResponseResult deleteAlbum(@PathVariable String id){
        try {
            service.deleteAlbum(id);
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
            List<StoreAlbum> storeAlbums =  service.allStoreAlbum(id);
            QueryResult<StoreAlbum> result = new QueryResult<>();
            result.setData(storeAlbums);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询当前{}相册失败",id);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    /**
     * 查询所有数据与商铺搜索
     * @param param
     * @return com.rysh.system.response.QueryResponseResult
     * @author Hsiang Sun
     * @date 2019/9/19 16:58
     */
    private QueryResponseResult featchDate(ParamBean param){
        PageInfo<Store> farms = service.search(param);//调用同一个方法
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }


}
