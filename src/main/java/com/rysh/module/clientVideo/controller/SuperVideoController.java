package com.rysh.module.clientVideo.controller;
import com.rysh.module.classroom.beans.Video;
import com.rysh.module.clientVideo.service.SuperVideoService;
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
@RequestMapping(value = "/client/superVideo")
@Api(description = "移动端视频接口")
public class SuperVideoController {
    @Autowired
    private SuperVideoService superVideoService;

    private SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    @ApiOperation(value = "查询所有视频",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sortState",value = "不传 时间倒序   1点赞倒序"),
            @ApiImplicitParam(name = "token",value = "token")
    })
    @PostMapping(value = "/findAllVideo")
    public QueryResponseResult findAllVideo(Integer sortState,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<Video> videos = superVideoService.findAllVideo(uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(videos);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询所有视频异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    @ApiOperation(value = "给视频点赞",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoId",value = "视频id"),
            @ApiImplicitParam(name = "token",value = "token")
    })
    @PostMapping(value = "/setUP")
    public QueryResponseResult setUP(String videoId,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                if(superVideoService.setUp(videoId,uid)){
                    return new QueryResponseResult(CommonCode.SUCCESS);
                }else {
                    return new QueryResponseResult(CommonCode.FAIL);
                }

            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("视频点赞异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
