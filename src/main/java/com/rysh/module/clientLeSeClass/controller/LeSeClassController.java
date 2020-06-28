package com.rysh.module.clientLeSeClass.controller;

import com.rysh.module.beOmnipotentScore.mapper.ScoreMapper;
import com.rysh.module.clientLeSeClass.beans.LeSe;
import com.rysh.module.clientLeSeClass.beans.SuperLeSe;
import com.rysh.module.clientLeSeClass.service.LeSeClassService;
import com.rysh.module.garbage.beans.Garbage;
import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/client/LeSe")
@Log4j2
@Api(description = "移动端垃圾分类")
public class LeSeClassController {
    @Autowired
    private LeSeClassService leSeClassService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询垃圾类别
     * @param token
     * @return
     */
    @ApiOperation(value = "查询垃圾类别",httpMethod = "POST")
    @PostMapping(value = "/findLeSeByToken")
    public QueryResponseResult findLeSeCategoryByToken(String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                LeSe leSe=leSeClassService.findLeSeCategoryByToken(uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(leSe);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询垃圾类别异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 通过垃圾分类id查询垃圾
     * @param categoryId
     * @return
     */
    @ApiOperation(value = "通过垃圾分类id查询垃圾",httpMethod = "POST")
    @PostMapping(value = "/findLeSeByCateGoryId")
    public QueryResponseResult findLeSeByCateGoryId(String categoryId){
        try {
            List<Garbage> garbages=leSeClassService.findLeSeByCategoryId(categoryId);
            QueryResult<Object> result = new QueryResult<>();
            result.setData(garbages);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            log.error("通过垃圾分类id查询垃圾异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    @ApiOperation(value = "垃圾搜索",httpMethod = "POST")
    @PostMapping(value = "/searchLeSe")
    public QueryResponseResult searchLeSe(String search,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<SuperLeSe> superLeSes=leSeClassService.searchLeSe(search,uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(superLeSes);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("垃圾搜索异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
