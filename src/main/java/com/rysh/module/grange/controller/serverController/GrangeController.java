package com.rysh.module.grange.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.DisplayInfo;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.beans.GrangeAlbum;
import com.rysh.module.grange.beans.GrangeInfo;
import com.rysh.module.grange.service.GrangeService;
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
@RequestMapping("/server/grange")
@Log4j2
@Api(description = "农庄API")
public class GrangeController implements ServerUserControllerApi {

    @Autowired
    private GrangeService service;

    @ApiOperation("新增农庄 传入name和address default_sort")
    @PostMapping("/add")
    public QueryResponseResult addGrange(@RequestBody Grange grange){
        int i = service.addNewGrange(grange);
        if ( i != 1){
            return new QueryResponseResult(CommonCode.FAIL);
        }else {
            return new QueryResponseResult(CommonCode.SUCCESS);
        }
    }

    @ApiOperation("所有的农庄信息 用于首次fetchDate")
    @GetMapping("/all")
    public QueryResponseResult allFarm(ParamBean param){
        return featchDate(param);
    }

    @ApiOperation("根据农庄id获取当前农场信息 用于数据回显")
    @GetMapping("/grange/{id}")
    public QueryResponseResult getFarmById(@PathVariable String id){
        GrangeInfo grange = service.grangeById(id);
        if (grange == null){
            return new QueryResponseResult(CommonCode.FAIL);
        }
        QueryResult<GrangeInfo> result = new QueryResult<>();
        result.setData(grange);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("农庄基本信息的更新 数据回显后的更新")
    @PutMapping("/info/update")
    public QueryResponseResult updateGrangeInfo(@RequestBody GrangeInfo grangeInfo){
        try {
            service.updateGrangeInfo(grangeInfo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("农庄基本信息更新失败QAQ"+e);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("当前农庄的基本信息")
    @GetMapping("/info/{id}")
    public QueryResponseResult farmInfo(@PathVariable String id){
        QueryResult<DisplayInfo> result = new QueryResult<>();
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            DisplayInfo displayInfo = service.grangeInfo(login,id);
            result.setData(displayInfo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("获取农庄[{}]基本信息失败",id);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("农庄信息更新")
    @PostMapping ("/update")
    public QueryResponseResult updateFarm(@RequestBody Grange grange){
        int i = service.updateGrange(grange);
        if ( i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新农庄排序大小")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "需要更新的id"),
            @ApiImplicitParam(name = "value",value = "更新后的大小")
    })
    @PutMapping("/sort/{id}/{value}")
    public QueryResponseResult updateSort(@PathVariable String id,@PathVariable String value){
        int sortValue = Integer.parseInt(value);
        try {
            service.updateSort(id,sortValue);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新农庄排序大小失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除农庄")
    @DeleteMapping("/delete/{id}")
    public QueryResponseResult deletFarm(@PathVariable String id){
        int i = service.deletGrange(id);
        if (i == 1){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("农庄搜索")
    @GetMapping("/findAllGrange")
    public QueryResponseResult searchFarm(ParamBean param){
        return featchDate(param);
    }

    /**
     * 查询所有数据与农场搜索
     * @param param
     * @return com.rysh.system.response.QueryResponseResult
     * @author Hsiang Sun
     * @date 2019/9/19 16:58
     */
    private QueryResponseResult featchDate(ParamBean param){
        PageInfo<Grange> farms = service.search(param);//调用同一个方法
        QueryResult result = new QueryResult();
        result.setData(farms);
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }

    @ApiOperation("添加相册banner")
    @PostMapping("/album/banner/add/{id}")
    public QueryResponseResult addBannerAlbum(@RequestParam("file") MultipartFile file, @PathVariable String id){
        try {
            GrangeAlbum storeAlbum = service.addBannerAlbum(file,id);
            QueryResult<GrangeAlbum> result = new QueryResult<>();
            result.setData(storeAlbum);
            return new QueryResponseResult( CommonCode.SUCCESS,result);
        } catch (RuntimeException e) {
            log.error("{}添加农庄相册banner失败",new Date());
            return new QueryResponseResult(CommonCode.BEYOND_LIMITNUM);
        }
    }

    @ApiOperation("添加相册cover")
    @PostMapping("/album/cover/add/{id}")
    public QueryResponseResult addCoverAlbum(@RequestParam("file") MultipartFile file,@PathVariable String id){
        try {
            GrangeAlbum storeAlbum = service.addCoverAlbum(file, id);
            QueryResult<GrangeAlbum> result = new QueryResult<>();
            result.setData(storeAlbum);
            return new QueryResponseResult( CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("{}添加农庄相册cover失败",new Date());
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

    @ApiOperation(value = "查询当前农庄的相册内容",notes = "内部账号请传id 外部为null")
    @GetMapping("/album/all/{id}")
    public QueryResponseResult getAllStoreAlbum(@PathVariable String id){
        try {
            List<GrangeAlbum> storeAlbums =  service.allStoreAlbum(id);
            QueryResult<GrangeAlbum> result = new QueryResult<>();
            result.setData(storeAlbums);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询当前{}相册失败",id);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }



    @ApiOperation("农庄上市")
    @PutMapping("/apply/{operation}/{id}")
    public QueryResponseResult applyToClient(@PathVariable String id,@PathVariable String operation) {
        int i = service.applyToClient(id,operation);
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


}
