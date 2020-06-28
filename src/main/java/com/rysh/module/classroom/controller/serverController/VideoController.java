package com.rysh.module.classroom.controller.serverController;

import com.github.pagehelper.PageInfo;
import com.rysh.module.classroom.beans.Video;
import com.rysh.module.classroom.beans.VideoIds;
import com.rysh.module.classroom.beans.VideoVo;
import com.rysh.module.classroom.service.VideoService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/server/video")
@Api(description = "视频接口")
@Log4j2
public class VideoController {

    @Autowired
    private VideoService service;

    @ApiOperation(value = "添加视频",notes = "请点击Model查看具体含义")
    @PostMapping("/add")
    public QueryResponseResult addVideo(@RequestBody VideoVo videoVo){
        try {
            service.addVideo(videoVo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("添加视频失败！");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查看所有的视频")
    @GetMapping("/all")
    public QueryResponseResult allVideo(ParamBean paramBean){
        try {
            PageInfo<Video> videos = service.findAllVideo(paramBean);
            QueryResult<PageInfo<Video>> result = new QueryResult<>();
            result.setData(videos);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("编辑回显")
    @GetMapping("/info/{id}")
    @ApiImplicitParam(name = "id",value = "视频id")
    public QueryResponseResult videoInfo(@PathVariable String id){
        try {
            Video resultVideo = service.findVideoById(id);
            QueryResult<Video> result = new QueryResult<>();
            result.setData(resultVideo);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("根据当前id{}回显失败",id);
        return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新视频")
    @PutMapping("/update")
    public QueryResponseResult updateVideo(@RequestBody VideoVo videoVo){
        try {
            service.updateVideo(videoVo);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新视频{}失败",videoVo.getId());
        return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("更新视频排序")
    @PutMapping("/sort/{id}/{value}")
    public QueryResponseResult updateSort(@PathVariable String id,@PathVariable String value){
        int valueInt = Integer.parseInt(value);
        try {
            service.updateSort(id,valueInt);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            log.error("更新视频排序失败 id={},value={}",id,value);
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/delete/{id}")
    @ApiImplicitParam(name = "id",value = "视频id")
    public QueryResponseResult deleteVideo(@PathVariable String id){
        try {
            service.deleteVideo(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("查询所有的待审核视频")
    @GetMapping("/uncheck")
    public QueryResponseResult needCheckCategory(ParamBean paramBean){
        try {
            PageInfo<Video> categories = service.allNeedCheck(paramBean);
            QueryResult result = new QueryResult();
            result.setData( categories );
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("待审核视频 详情(回显)")
    @GetMapping("/uncheck/{id}")
    public QueryResponseResult oneUncheck(@PathVariable String id){
        try {
            Video farmItemList = service.getOneUncheck(id);
            QueryResult result = new QueryResult();
            result.setData(farmItemList);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("一键审核 【传入需要审核的视频id】")
    @PutMapping("/check/{operation}")
    public QueryResponseResult checkMany(@RequestBody VideoIds videoIds, @PathVariable String operation){
        int i = service.checkPass(videoIds,operation);
        if ( videoIds.getIds().size() == i){
            return new QueryResponseResult(CommonCode.SUCCESS);
        }else {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("视频审核历史记录")
    @GetMapping("/check/history")
    public QueryResponseResult getCheckHistory(ParamBean paramBean){
        try {
            PageInfo<Video> farms = service.checkHistory(paramBean);
            QueryResult result = new QueryResult();
            result.setData(farms);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }

    @ApiOperation("视频上/下架")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "operation",value = "上架:up 下架:down"),
            @ApiImplicitParam(name = "id",value = "视频id")
    })
    @PutMapping("/apply/{operation}/{id}")
    public QueryResponseResult applyToClient(@PathVariable String operation,@PathVariable String id){
        try {
            service.upOrDownApply(operation,id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (Exception e) {
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }




}
