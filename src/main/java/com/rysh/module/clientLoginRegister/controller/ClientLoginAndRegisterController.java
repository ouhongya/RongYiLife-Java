package com.rysh.module.clientLoginRegister.controller;

import com.rysh.module.beOmnipotentScore.beans.ScoreDetail;
import com.rysh.module.beOmnipotentScore.beans.UserScoreDetail;
import com.rysh.module.beOmnipotentScore.service.ScoreService;
import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientLoginRegister.service.LoginAndRegisterService;
import com.rysh.module.commonService.service.ImageUploadService;
import com.rysh.module.utils.CheckRedisTokenUtils;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequestMapping(value = "/client/loginAndRegister")
@Api(description = "移动端注册，登陆，发送验证码短信接口")
public class ClientLoginAndRegisterController {
    @Autowired
    private LoginAndRegisterService loginAndRegisterService;

    @Autowired
    private CheckRedisTokenUtils checkRedisTokenUtils;

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ScoreService scoreService;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 用户登陆注册
     * @param loginName  登陆名 手机号
     * @param code  用户输入的验证码
     * @return
     */
    @ApiOperation(value = "移动端登陆",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName",value = "登录名  手机号"),
            @ApiImplicitParam(name = "code",value = "验证码")
    })
    @PostMapping(value = "/login")
    public QueryResponseResult login(String loginName,String code){
        int status = 0;
        Map<String, Object> loginMap;
        try {
            loginMap = loginAndRegisterService.login(loginName, code);
            QueryResult<Object> result = new QueryResult<>();
            status = (int) loginMap.get("state");
            if(status==1){
                //登陆成功
                result.setData(loginMap.get("token"));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else if(status==2){
                //未绑定小区
                result.setData(loginMap.get("token"));
                return new QueryResponseResult(CommonCode.NOT_BINDING_COMMUNITY,result);
            }else if(status==3) {
                //验证码错误
                return new QueryResponseResult(CommonCode.VERIFICATION_ERROR);
            }else if(status==4){
                //未发送验证码
                return new QueryResponseResult(CommonCode.NOT_FIND_CODE);
            }else {
                //系统错误
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("登陆异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 发送验证码
     * @param phoneNum  手机号
     * @return
     */
    @ApiOperation(value = "发送验证码短信",httpMethod = "POST")
    @ApiImplicitParam(name = "phoneNum",value = "电话号码")
    @PostMapping(value = "/send")
    public QueryResponseResult send(String phoneNum){
        String status="";
        try {
            status= loginAndRegisterService.sendMessage(phoneNum);
            if("isv.AMOUNT_NOT_ENOUGH".equals(status)){
                log.error("阿里云账户余额不足----短信");
                return new QueryResponseResult(CommonCode.SERVER_ERROR);
            }
            if("isv.MOBILE_NUMBER_ILLEGAL".equals(status)){
                log.info("非法手机号");
                return new QueryResponseResult(CommonCode.NOT_FIND_PHONE);
            }
            if("isv.BUSINESS_LIMIT_CONTROL".equals(status)){
                log.info("该手机号短信接收已达上限");
                return new QueryResponseResult(CommonCode.UPPER_LIMIT);
            }
            if("OK".equals(status)){
                return new QueryResponseResult(CommonCode.SUCCESS);
            }
            log.error(status);
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        } catch (Exception e) {
            log.error("发送验证异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 查询当前登陆用户信息
     * @param token token字符串
     * @return
     */
    @ApiOperation(value = "查询当前登陆用户信息",httpMethod = "POST")
    @ApiImplicitParam(name = "token",value = "token字符串")
    @PostMapping(value = "/findNowUser")
    public QueryResponseResult findNowUser(String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid==null){
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }else {
                QueryResult<Object> result = new QueryResult<>();
                result.setData(loginAndRegisterService.findNowUser(uid));
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }
        }catch (Exception e){
            log.error("获取当前用户信息异常 client");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    /**
     * 登陆成功后编辑用户信息
     * @param user
     * @return
     */
    @ApiOperation(value = "登陆成功后编辑用户信息",httpMethod = "POST",notes = "token,name(用户昵称),communityId(小区Id),avatar(用户头像url)")
    @PostMapping(value = "/updateUser")
    public QueryResponseResult updateUser(User user){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(user.getToken());
            if(uid==null){
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }else {
                user.setId(uid);
                loginAndRegisterService.updateUserById(user);
                return new QueryResponseResult(CommonCode.SUCCESS);
            }
        }catch (Exception e){
            log.error("编辑用户信息异常  client");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


    /**
     * 上传头像
     * @param token
     * @param file
     * @return
     */
    @PostMapping(value = "/uploadHead")
    @ApiOperation(value = "用户上传头像",httpMethod = "POST",notes = "token , file(头像图片文件)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "token",value = "token"),
                    @ApiImplicitParam(name = "file",value = "头像图片文件")
            }
    )
    public QueryResponseResult uploadHead(@RequestParam MultipartFile file,String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid==null){
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }else {
              if(file!=null){
                  String url = imageUploadService.upload(file);
                  loginAndRegisterService.updateUserAvatar(uid,url);
                  QueryResult<Object> result = new QueryResult<>();
                  result.setData(url);
                  return new QueryResponseResult(CommonCode.SUCCESS,result);
              }else {
                  return new QueryResponseResult(CommonCode.SERVER_ERROR);
              }
            }
        }catch (Exception e){
            log.error("头像上传异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }

    }

    /**
     * 查询用户积分来源
     * @param token
     * @return
     */
    @ApiOperation(value = "查询用户积分")
    @ApiImplicitParam(name = "token",value = "token")
    @PostMapping(value = "/findUserScoreSource")
    public QueryResponseResult findUserScoreSource(String token){
        try {
            String uid = checkRedisTokenUtils.checkRedisToken(token);
            if(uid!=null){
                List<UserScoreDetail> userScoreSource = scoreService.findUserScoreSource(uid);
                QueryResult<Object> result = new QueryResult<>();
                result.setData(userScoreSource);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                return new QueryResponseResult(CommonCode.TOKEN_ERROR);
            }
        }catch (Exception e){
            log.error("查询用户积分来源异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
