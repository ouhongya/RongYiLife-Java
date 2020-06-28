package com.rysh.module.serverSystem.controller;

import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.Store;
import com.rysh.module.serverSystem.service.StoreService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
@Log4j2
@RestController
@RequestMapping("/server/unitshop")
@Api(description = "商铺设置接口")
public class ServerStoreController {
    @Autowired
    private StoreService storeService;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //时间转字符串

    /**
     * 添加商铺
     * @param store
     * @return
     */
    @PostMapping("/addStore")
    @PreAuthorize("hasPermission(null ,null )")
    public QueryResponseResult addStore(@RequestBody Store store){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(storeService.addStore(store)){
                result.setData("添加成功");
            }
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加商铺异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("添加商铺异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 查询所有商铺
     * @param paramBean
     * @return
     */
    @GetMapping(value = "/findAllStore")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findAllStore(ParamBean paramBean){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(paramBean.getSearch()==null||"".equals(paramBean.getSearch())){
                result.setData(storeService.findAllStore(paramBean));
            } else {
                result.setData(storeService.searchAllStore(paramBean));
            }
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询所有商铺异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("查询所有商铺异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    @GetMapping(value = "/findStoreById/{storeId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findStoreById(@PathVariable(name = "storeId") String storeId){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(storeService.findStoreById(storeId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("通过id查询商铺异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("通过id查询商铺异常");
            return new QueryResponseResult(CommonCode.FAIL, result);
        }
    }

    /**
     * 编辑商铺
     * @param store   商铺对象
     * @return
     */
    @PostMapping(value = "/updateStore")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult updateStore(@RequestBody Store store){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(storeService.updateStore(store));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("编辑商铺异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("编辑商铺异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    @DeleteMapping(value = "/deleteStore/{storeId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult deleteStore(@PathVariable(name = "storeId") String storeId){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(storeService.deleteStore(storeId)){
                result.setData("删除成功");
            }
            return new QueryResponseResult(CommonCode.SUCCESS,result);
            }catch (Exception e){
            log.error("删除商铺异常");
            }
            return null;
    }


}
