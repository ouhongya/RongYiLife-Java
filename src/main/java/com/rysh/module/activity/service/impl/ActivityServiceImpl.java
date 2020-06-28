package com.rysh.module.activity.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.activity.beans.*;
import com.rysh.module.activity.mapper.ActivityMapper;
import com.rysh.module.activity.service.ActivityService;
import com.rysh.module.clientMessage.beans.Message;
import com.rysh.module.clientMessage.mapper.MessageMapper;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.User;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.utils.GenerateUUID;
import com.rysh.module.utils.ToNullUtil;
import com.rysh.module.webSocket.WebSocket;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper mapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    public int addActivity(ActivityVo activityVo) {

        Activity activity = new Activity();
        String activityId = GenerateUUID.create();
        activity.setId(activityId);
        activity.setName(activityVo.getName());
        activity.setStartTime(activityVo.getStartTime());
        activity.setEndTime(activityVo.getEndTime());
        activity.setMember(activityVo.getMember());
        activity.setFreeMember(activityVo.getMember());
        activity.setLocation(activityVo.getLocation());
        activity.setPublisher(activityVo.getPublisher());
        activity.setPublisherUnit(activityVo.getPublisherUnit());
        activity.setContactNum(activityVo.getContactNum());
        activity.setPass(0);//初始未通过审核
        activity.setPassOperator("System");
        activity.setPassTime(null);
        activity.setPassComment(null);
        activity.setCreatedTime(new Date());
        activity.setApplyStartTime(activityVo.getApplyStartTime());
        activity.setApplyEndTime(activityVo.getApplyEndTime());
        activity.setAreaId(activityVo.getAreaId());

        int row = mapper.insert(activity);

        //添加活动图片
        List<ActivityImg> imgs = activityVo.getImgs();
        int imgRow = 0 ;
        for (ActivityImg img : imgs) {
            ActivityImg activityImg = new ActivityImg();
            activityImg.setId(GenerateUUID.create());
            activityImg.setUrl(img.getUrl());
            activityImg.setLocation(img.getLocation());
            activityImg.setActivityId(activityId);
            activityImg.setCreatedTime(new Date());
            activityImg.setStatus(1);

            int currentRow = mapper.addActivityImg(activityImg);
            imgRow += currentRow ;
        }

        //添加活动内容

        List<ActivityContent> contents = activityVo.getContents();
        int contentRow = 0;
        for (ActivityContent content : contents) {
            ActivityContent activityContent = new ActivityContent();
            activityContent.setId(GenerateUUID.create());
            activityContent.setContent(content.getContent());
            activityContent.setQueue(content.getQueue());
            activityContent.setActivityId(activityId);

            int currentRow = mapper.addActivityContent(activityContent);
            contentRow += currentRow ;
        }


        //向前端发送消息
        webSocket.sendMsg("AB75BA49CF384764ACC1A4D0AE227B2B","有新的公益活动来了");
        return row + imgRow + contentRow;
    }

    @Override
    public int delete(String id) {
        return mapper.delete(id);
    }

    @Override
    @Transactional
    public void update(ActivityVo activityVo) {

        //判断当前活动是否已经有用户报名参加了
        int recode = mapper.checkHasUserJoin(activityVo.getId());
        if (recode > 0){
            log.error("当前活动["+ activityVo.getId()+"] 已经有用户参加了 不能进行修改");
            throw new RuntimeException("当前活动["+ activityVo.getId()+"] 已经有用户参加了 不能进行修改");
        }

        Activity activity = new Activity();
        activity.setId(activityVo.getId());
        activity.setName(activityVo.getName());
        activity.setStartTime(activityVo.getStartTime());
        activity.setEndTime(activityVo.getEndTime());
        activity.setMember(activityVo.getMember());
        activity.setFreeMember(activityVo.getMember());
        activity.setLocation(activityVo.getLocation());
        activity.setPublisher(activityVo.getPublisher());
        activity.setPublisherUnit(activityVo.getPublisherUnit());
        activity.setContactNum(activityVo.getContactNum());
        activity.setPass(0);//重新审核
        activity.setPassOperator("System");
        activity.setPassTime(null);
        activity.setPassComment(null);
        activity.setCreatedTime(activityVo.getCreatedTime());
        activity.setStatus(1);
        activity.setApplyStartTime(activityVo.getApplyStartTime());
        activity.setApplyEndTime(activityVo.getApplyEndTime());
        activity.setAreaId(activityVo.getAreaId());

        mapper.update(activity);

        List<ActivityImg> imgs = activityVo.getImgs();
        mapper.deleteActivityImg(activityVo.getId());
        for (ActivityImg img : imgs) {
            ActivityImg activityImg = new ActivityImg();
            activityImg.setId(GenerateUUID.create());
            activityImg.setUrl(img.getUrl());
            activityImg.setLocation(img.getLocation());
            activityImg.setActivityId(activityVo.getId());
            activityImg.setCreatedTime(new Date());
            activityImg.setStatus(1);
            mapper.updateActivityImg(activityImg);
        }

        List<ActivityContent> contents = activityVo.getContents();
        mapper.deleteActivityContent(activityVo.getId());
        for (ActivityContent content : contents) {
            ActivityContent activityContent = new ActivityContent();
            activityContent.setId(GenerateUUID.create());
            activityContent.setContent(content.getContent());
            activityContent.setQueue(content.getQueue());
            activityContent.setActivityId(activityVo.getId());
            mapper.updateActivityContent(activityContent);
        }
    }

    @Override
    public PageInfo<ActivityVo> all(ParamBean paramBean,int option) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        Date now = new Date();
        String searchParam = paramBean.getSearch();
        String search = ToNullUtil.toNull(searchParam);
        List<ActivityVo> results = mapper.findAll(search,option,now);
        for (ActivityVo result : results) {
            result.setFreeMember(result.getMember() - mapper.findJoinedNumByActivityId(result.getId()));
        }
        return new PageInfo<>(results);
    }

    @Override
    public int userSignUp(ActivityUser activityUser, String login) {
        //判断当前时间
        Date currentTime = new Date();
        long now = currentTime.getTime();
        //参加活动的Id
        String activityId = activityUser.getActivityId();

        Activity activity = mapper.findById(activityId);

        if (activity != null){

            //判断当前剩余名额是否已用完
            int freeMember = activity.getFreeMember();
            if (freeMember < 1){
                log.error("当前活动【"+activityId+"】已经没有剩余名额了");
                return 0;
            }
            //判断当前用户是否已经报过名
            int recode = mapper.isExist(activityUser.getActivityId(),login);
            if (recode > 0){
                log.error("当前用户["+login+"] 已经报名活动["+activityUser.getActivityId()+"]");
                return 0;
            }

            Date applyStartTime = activity.getApplyStartTime();
            long startTime = applyStartTime.getTime();
            Date applyEndTime = activity.getApplyEndTime();
            long endTime = applyEndTime.getTime();
            //判断当前时间是否是可以报名
            if (startTime < now && now < endTime){
                //查询当前用户Id
                activityUser.setUserId(login);
                activityUser.setCreatedTime(new Date());

                //开始报名
                int row = mapper.activitySignUp(activityUser);
                //用户报名成功 减少活动可参加人数
                mapper.reduceNum(activityId);
                //创建消息对象
                Message message=new Message();
                message.setId(GenerateUUID.create());
                message.setContent("尊敬的用户，你参加的"+activity.getName()+"，已成功报名。");
                message.setUserId(login);
                message.setCreatedTime(new Date());
                messageMapper.sendMessage(message);
                return row;
            }else {
                log.error("当前活动已经关闭或者尚未开始");
                return 0;
            }
        }else {
            log.error("当前id"+activityId+"已经被删除或者未找到该活动");
            return 0;
        }
    }

    @Override
    public PageInfo<Activity> uncheckActivity(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled() + " "+paramBean.getSortByOrder());
        List<Activity> activityList = mapper.findUncheck();
        return new PageInfo<>(activityList);
    }

    @Override
    public void checkActivity(List<String> ids, String operation,String login,String passComment) {
        Date now = new Date();
        mapper.check(ids,operation,login,now,passComment);
    }

    @Override
    public PageInfo<Activity> checkHistory(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        String orderBy=paramBean.getSortByFiled() + " "+paramBean.getSortByOrder();
        PageHelper.orderBy(orderBy);
        List<Activity> farmViewList = mapper.findCheckHistory(paramBean.getSearch());
        return new PageInfo<Activity>(farmViewList);
    }

    @Override
    public void signMany(List<String> ids) {
        mapper.signMany(ids);
    }

    @Override
    public PageInfo<JoinActivityUser> getUserByActivityId(ParamBean paramBean, String id) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled() + " "+paramBean.getSortByOrder());
        List<JoinActivityUser> resultList = mapper.findJoinUserByActivityId(id);
        return new PageInfo<>(resultList);
    }

    @Override
    public List<JoinActivityUser> getUserByActivityId(String id) {
        return mapper.findJoinUserByActivityId(id);
    }

    @Override
    public PageInfo<ActivityStatistics> ActivityStatistics(TimeParam timeParam, Date startTime, Date endTime) {
        PageHelper.startPage(timeParam.getPage(),timeParam.getSize());
        PageHelper.orderBy(timeParam.getSortByFiled()+" "+timeParam.getSortByOrder());
        List<ActivityStatistics> resultList = mapper.ActivityStatisticsByTime(startTime,endTime);
        return new PageInfo<ActivityStatistics>(resultList);
    }

    @Override
    public ClientRankResponse clientFamilyRank() {
        List<ClientRank> clientRank = mapper.findClientRank();
        String banner = null;
        String introduction = null;
        List<ClientRankUserInfo> clientRankUserInfos = new ArrayList<>();
        for (ClientRank rank : clientRank) {
            banner = rank.getBanner();
            introduction = rank.getIntroduction();
            ClientRankUserInfo userInfo = new ClientRankUserInfo();
            userInfo.setNo(rank.getNo());
            userInfo.setAvatar(rank.getAvatar());
            userInfo.setName(rank.getName());
            clientRankUserInfos.add(userInfo);
        }
        ClientRankResponse clientRankResponse = new ClientRankResponse();
        clientRankResponse.setBanner(banner);
        clientRankResponse.setIntroduction(introduction);
        clientRankResponse.setDetail(clientRankUserInfos);

        return clientRankResponse;
    }

    @Override
    public PageInfo<Activity> searchActivity(ParamBean paramBean) {
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Activity> resultList = mapper.findByName(paramBean.getSearch());
        return new PageInfo<>(resultList);
    }

    @Override
    public ActivityVo getActivityVoById(String id) {
        return mapper.findActivityAllInfoById(id);
    }
}
