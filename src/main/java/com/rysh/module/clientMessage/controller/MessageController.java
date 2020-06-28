package com.rysh.module.clientMessage.controller;
import com.rysh.module.clientMessage.beans.SuperMessage;
import com.rysh.module.clientMessage.service.MessageService;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/client/message")
@Api(description = "移动端消息接口")
public class MessageController {
    @Autowired
    private MessageService messageService;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    /**
     * 查询消息
     * @param token
     * @param state 1已读  0未读  2全部 都按时间正序排列
     * @param time  1近一个月   2近两个月  3近三个月  以此类推
     * @return
     */
    @ApiOperation(value = "查询消息",httpMethod = "POST",response = SuperMessage.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "token"),
            @ApiImplicitParam(name = "state",value = "1已读 0未读 2全部  都按时间正序排列"),
            @ApiImplicitParam(name = "time",value = "1近一个月   2近两个月  3近三个月  以此类推")
    })
    @PostMapping(value = "/findMessage")
    public QueryResponseResult findMessage(String token,Integer state,Integer time){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                SuperMessage superMessage= messageService.findMessage(uid,state,time);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(superMessage);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询消息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
