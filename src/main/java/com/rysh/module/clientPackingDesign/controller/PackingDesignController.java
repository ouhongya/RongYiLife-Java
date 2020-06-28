package com.rysh.module.clientPackingDesign.controller;


import com.rysh.module.clientPackingDesign.beans.PackingDesign;
import com.rysh.module.clientPackingDesign.beans.PackingDesignCategory;
import com.rysh.module.clientPackingDesign.beans.PackingDesignDetail;
import com.rysh.module.clientPackingDesign.service.PackingDesignService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api(description = "移动端社群定制")
@Log4j2
@RestController
@RequestMapping(value = "/client/packingDesignPlus")
public class PackingDesignController {

    @Autowired
    private PackingDesignService packingDesignService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @ApiOperation(value = "查询社群定制",httpMethod = "POST",response = PackingDesign.class,notes = "首页显示 size 传20    查询全部就按情况来")
    @ApiImplicitParam(name = "categoryId",value = "社群定制分类id   可以不传")
    @PostMapping(value = "/findPackingDesign")
    public QueryResponseResult findPackingDesign(ParamBean paramBean,String categoryId){
        try {
            List<PackingDesign> packingDesigns=packingDesignService.findPackingDesign(paramBean,categoryId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(packingDesigns);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询社群定制异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "通过id查询社群定制详情",httpMethod = "POST",response = PackingDesignDetail.class)
    @ApiImplicitParam(name = "id",value = "社群定制id")
    @PostMapping(value = "/findPackingDesignDetailById")
    private QueryResponseResult findPackingDesignDetailById(String id){
        try {
            List<PackingDesignDetail> packingDesignDetails = packingDesignService.findPackingDesignDetailById(id);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(packingDesignDetails);
            return new QueryResponseResult(CommonCode.SUCCESS, result);
        } catch (Exception e) {
            log.error("查询社群定制详情异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "查询社群定制分类",httpMethod = "POST",response = PackingDesignCategory.class)
    @PostMapping(value = "/findPackingDesignCategory")
    private QueryResponseResult findPackingDesignCategory(ParamBean paramBean){
        try {
            List<PackingDesignCategory> packingDesignCategories=packingDesignService.findPackingDesignCategory(paramBean);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(packingDesignCategories);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询社群定制分类异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "社群定制留言",httpMethod = "POST")
    @PostMapping(value = "/addMessageToP")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tel",value = "联系电话"),
            @ApiImplicitParam(name = "name",value = "联系人名字"),
            @ApiImplicitParam(name = "customizedId",value = "社群定制id")
    })
    public QueryResponseResult addMessageToP(String tel,String name,String customizedId){
                try {
                    packingDesignService.addMessageToP(tel,name,customizedId);
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }catch (Exception e){
                    log.error("社群定制留言异常");
                    log.error(e);
                    log.error(sf.format(new Date()));
                    return new QueryResponseResult(CommonCode.SERVER_ERROR);
                }
    }
}
