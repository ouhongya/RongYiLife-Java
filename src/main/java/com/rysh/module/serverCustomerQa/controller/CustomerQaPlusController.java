package com.rysh.module.serverCustomerQa.controller;

import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverCustomerQa.beans.CustomerQaPlus;
import com.rysh.module.serverCustomerQa.service.CustomerQaPlusService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@Log4j2
@RequestMapping(value = "/server/qa")
@Api(description = "客服问答")
public class CustomerQaPlusController {

    @Autowired
    private CustomerQaPlusService customerQaPlusService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询客服问答
     * @return
     */
    @GetMapping(value = "/findCustomerQa")
    @ApiOperation(value = "查询所有问答")
    public QueryResponseResult findCustomerQa(ParamBean paramBean){
        try {

            QueryResult<Object> result = new QueryResult<>();
            result.setData(customerQaPlusService.findAllCustomerQa(paramBean));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("查询客服问答异常  -后端");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 添加问答
     * @param customerQaPlus
     * @return
     */
    @PostMapping(value = "/addCustomerQa")
    @ApiOperation(value = "添加问答",notes = "需要参数 ： 问题（question）  答案（answer）  排序字段（defaultSort  越大 排名越靠前）")
    public QueryResponseResult addCustomerQa(CustomerQaPlus customerQaPlus){
        try {
            customerQaPlusService.addCustomerQa(customerQaPlus);
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("添加问答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation("更新排序字段")
    @PutMapping("/sort/{id}/{value}")
    public QueryResponseResult updateSort(@PathVariable String id,@PathVariable String value){
        try {
            int valueInt = Integer.parseInt(value);
            customerQaPlusService.updateSort(id,valueInt);
            return new QueryResponseResult(CommonCode.SUCCESS);
        } catch (NumberFormatException e) {
            log.error("更新问答排序字段失败");
            return new QueryResponseResult(CommonCode.FAIL);
        }
    }


    /**
     * 编辑问答
     * @param customerQaPlus
     * @return
     */
    @PostMapping(value = "/updateCustomerQa")
    @ApiOperation(value = "编辑问答",notes = "需要参数 ：id   问题（question）  答案（answer） 排序字段（defaultSort）")
    public QueryResponseResult updateCunstomerQa(@RequestBody CustomerQaPlus customerQaPlus){
        try {
            customerQaPlusService.updateCustomerQaPlus(customerQaPlus);
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("编辑问答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 发布
     * @return
     */
    @PostMapping(value = "/UpperLower")
    @ApiOperation(value = "发布问答",notes = "需要参数 id   状态值（status  0未发布   1发布）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "问答id"),
            @ApiImplicitParam(name = "status",value = "状态值  0未发布  1发布")
    })
    public QueryResponseResult UpperLower(@RequestBody CustomerQaPlus customerQaPlus){
        try {
            customerQaPlusService.UpperLower(customerQaPlus.getId(),customerQaPlus.getStatus());
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("问答上下架异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 删除问答
     * @param id
     * @return
     */
    @DeleteMapping(value = "/deleteCustomerQa/{id}")
    @ApiOperation(value = "删除问答")
    public QueryResponseResult deleteCustomerQa(@PathVariable(name = "id") String id){
        try {
            customerQaPlusService.deleteCustomerQa(id);
            return new QueryResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error("删除问答异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
