package com.rysh.module.clientLoginRegister.service.impl;


import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientLoginRegister.mapper.LoginAndRegisterMapper;
import com.rysh.module.clientLoginRegister.service.LoginAndRegisterService;
import com.rysh.module.commonService.service.impl.SendMessage;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.mapper.AreaMapper;
import com.rysh.module.community.mapper.CityMapper;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.utils.TokenUtils;
import com.rysh.module.utils.encodeUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAndRegisterServiceImpl implements LoginAndRegisterService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LoginAndRegisterMapper loginAndRegisterMapper;

    @Autowired
    private encodeUtils encodeUtils;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CityMapper cityMapper;

    //验证码过期时间
    @Value("${result.codeExpirationTime}")
    private Integer codeExpirationTime;

    //手机token过期时间
    @Value("${result.clientExpirationTime}")
    private Long clientExpirationTime;

    //积分折扣比例
    @Value("${result.discountRate}")
    private Integer discountRate;

    //使用积分的最低消费限制
    @Value("${result.minimums}")
    private Integer minimums;

    /**
     * 登陆注册
     * @param phoneNum  手机号
     * @param code   验证码
     * @return  1=登陆成功   2=请发送验证码   3=验证码错误  4=请绑定小区
     **/
    public Map<String,Object> login(String phoneNum,String code) throws Exception{
        //通过手机号在redis中获取系统生成的验证码进行比较
        byte[] codeByte = (byte[]) redisTemplate.opsForValue().get(phoneNum.getBytes());
        //token初始化
        String encodeToken="";
        Map<String,Object> map=new HashMap<>();
        if (codeByte != null && codeByte.length > 0) {
            String systemCode = new String(codeByte);    //系统生成的随机验证码
            if (systemCode.equals(code)) {
                //验证码正确
                User user = loginAndRegisterMapper.findUserByPhoneNum(phoneNum);
                String communityId = user.getCommunityId();
                //为用户创建token
                Map<String,Object> tokenMap=new HashMap<>();
                tokenMap.put("uid",user.getId());
                //token生成时间
                tokenMap.put("iat",new Date().getTime());
                //token过期时间   3小时
                tokenMap.put("ext",new Date().getTime()+clientExpirationTime);
                //生成token
                String token = TokenUtils.createToken(tokenMap);
                //将token加密
                encodeToken=encodeUtils.encode(token);
                //将加密后的token存入redis
                redisTemplate.opsForValue().set(encodeToken.getBytes(),token.getBytes());
                if(communityId==null||"".equals(communityId)){
                    //未绑定小区
                    map.put("state",2);
                    map.put("token",encodeToken);
                }else {
                    //登陆成功 已经绑定了小区
                    map.put("token",encodeToken);
                    map.put("state",1);
                }
                //清除redis中的验证码信息
                redisTemplate.delete(phoneNum.getBytes());

            } else {
                //验证码错误
                map.put("state",3);
            }
        } else {
            //没有发送验证码
            map.put("state",4);
        }
        return map;
    }

    /**
     * 发送验证码短信
     * @param loginName  手机号
     * @throws Exception
     */
    public String sendMessage(String loginName) throws Exception{
        //生成一个6位的随机验证码
        String randomCode = "1234567890";
        Random random = new Random();
        StringBuilder codes=new StringBuilder();
        for (int i1 = 0; i1 < 4; i1++) {
          codes.append(randomCode.charAt(random.nextInt(randomCode.length() - 1)));
        }
        String code = codes.toString();

        //将生成的随机数验证码存入redis
        redisTemplate.opsForValue().set(loginName.getBytes(), code.getBytes());
        //给验证码设置过期时间
        redisTemplate.expire(loginName.getBytes(),codeExpirationTime, TimeUnit.SECONDS);
        //调用短信接口 给用户发送验证码短信
        String status = SendMessage.sendCode(loginName, code);
        if("OK".equals(status)){
            //判断该用户是否是第一次登陆
            User user = loginAndRegisterMapper.findUserByPhoneNum(loginName);
            if (user == null) {
                //将用户添加进数据库
                User user1 = new User();
                //设置用户对象的基本信息
                user1.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                user1.setLoginName(loginName);
                user1.setScore(0);
                user1.setCreatedTime(new Date());
                loginAndRegisterMapper.addUser(user1);
            }
        }
        return status;
    }

    /**
     * 查询当前用户信息
     * @param uid
     */
    @Override
    public User findNowUser(String uid) throws Exception {
        User user = loginAndRegisterMapper.findNowUser(uid);
            //查询用户所属小区名称
        if(user.getCommunityId()!=null){
            Community community = communityMapper.findCommunityById(user.getCommunityId());
            user.setCommunityName(community.getName());
            //查询小区所属区域
            Area area=areaMapper.findAreaById(community.getAreaId());
            user.setAreaName(area.getAreaName());
            //查询区域所属城市
            City city=cityMapper.findCityById(area.getCityId());
            user.setCityName(city.getCityName());
        }
        //设置积分折扣比例
        user.setDiscountRate(discountRate);
        //设置使用积分最低消费
        user.setMinimums(minimums);
        return user;
    }

    /**
     * 编辑用户信息
     * @param user
     * @throws Exception
     */
    @Override
    public void updateUserById(User user) throws Exception {
        loginAndRegisterMapper.updateUserById(user);
    }

    /**
     * 修改用户头像url
     * @param uid
     * @param url
     */
    @Override
    public void updateUserAvatar(String uid, String url) throws Exception {
        loginAndRegisterMapper.updateUserAvatar(uid,url);
    }


}
