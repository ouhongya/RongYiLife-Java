package com.rysh.module.clientSuperActivity.service.impl;

import com.rysh.module.activity.beans.ActivityContent;
import com.rysh.module.activity.beans.ActivityVo;
import com.rysh.module.clientSuperActivity.beans.ActivityPlus;
import com.rysh.module.clientSuperActivity.mapper.SuperActivityMapper;
import com.rysh.module.clientSuperActivity.service.SuperActivityService;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SuperActivityServiceImpl implements SuperActivityService {

    @Autowired
    private SuperActivityMapper superActivityMapper;


    /**
     * 查询活动
     * @param uid
     * @param paramBean
     * @param cityId
     * @param areaId
     * @return
     */
    @Override
    public List<ActivityPlus> findSuperActivity(String uid, ParamBean paramBean, String cityId, String areaId) {
        //初始化活动plus集合
        List<ActivityPlus> activityPluses=new ArrayList<>();
        List<ActivityVo> activities=superActivityMapper.findSuperActivity(paramBean.getSearch(),cityId,areaId);
        //设置活动图片和介绍
        for (ActivityVo activity : activities) {
            //创建活动plus对象
            ActivityPlus activityPlus= new ActivityPlus();
            //设置活动缩略图
            String activityCoverImg=superActivityMapper.findActivityCoverImg(activity.getId());
            activityPlus.setCover(activityCoverImg);
            //设置活动banner图
            List<String> activityBannerImgs=superActivityMapper.findActivityBannerImgs(activity.getId());
            activityPlus.setUrls(activityBannerImgs);
            //设置当前用户是否报名的活动
            String id = superActivityMapper.findActivityUser(uid, activity.getId());
            if(id!=null){
                activityPlus.setIsSignUp(false);
            }
            //设置剩余人数
            Integer integer = superActivityMapper.countActivityUserByActivityId(activity.getId());
            activityPlus.setFreeMember(activity.getMember()-integer);
            //活动报名是否开始
            if(activity.getApplyStartTime().getTime()<new Date().getTime()){
                activityPlus.setIsState(true);
            }
            //活动报名是否结束
            if(activity.getApplyEndTime().getTime()<new Date().getTime()){
                activityPlus.setIsState(false);
            }
            //活动是否结束
            if(activity.getEndTime().getTime()<new Date().getTime()){
                activityPlus.setIsState(false);
            }
            //活动id
            activityPlus.setId(activity.getId());
            //活动名称
            activityPlus.setName(activity.getName());
            //活动预聘人数
            activityPlus.setMember(activity.getMember());
            //活动地点
            activityPlus.setLocation(activity.getLocation());
            //活动发起人
            activityPlus.setPublisher(activity.getPublisher());
            //发起单位
            activityPlus.setPublisherUnit(activity.getPublisherUnit());
            //联系电话
            activityPlus.setContactNum(activity.getContactNum());
            //区域id
            activityPlus.setAreaId(activity.getAreaId());
            //活动内容
            List<ActivityContent> activityContents = superActivityMapper.findActivityContentByActivityId(activity.getId());
            for (ActivityContent activityContent : activityContents) {
                activityPlus.getContents().add(activityContent.getContent());
            }
            //设置时间毫秒值
            activityPlus.setApplyStartTimeMilli(activity.getApplyStartTime().getTime());
            activityPlus.setApplyEndTimeMilli(activity.getApplyEndTime().getTime());
            activityPlus.setStartTimeMilli(activity.getStartTime().getTime());
            activityPlus.setEndTimeMilli(activity.getEndTime().getTime());
            activityPluses.add(activityPlus);
        }
        return activityPluses;
    }


    /**
     * 活动报名
     * @param uid
     * @param activityId
     * @param name
     * @param phone
     * @return
     * @throws Exception
     */
    @Override
    public boolean activitySignUp(String uid, String activityId,String name,String phone) throws Exception {
        //使用lock锁解决线程安全问题
        Lock lock=new ReentrantLock();
        try {
            lock.lock();
            //查询活动信息
            ActivityVo activityVo=superActivityMapper.findActivityById(activityId);
            //剩余人数
            Integer countUser=superActivityMapper.countActivityUserByActivityId(activityId);
            //活动报名开始  报名未结束  活动未结束
            if(activityVo.getMember()>countUser&&
                    activityVo.getApplyStartTime().getTime()<new Date().getTime()&&
                    activityVo.getApplyEndTime().getTime()>new Date().getTime()&&
                    activityVo.getEndTime().getTime()>new Date().getTime()){
                //当招募人数大于剩余人数时
                //并且当前用户没有报名过此活动
                String id = superActivityMapper.findActivityUser(uid, activityVo.getId());
                if(id==null){
                    //报名成功
                    superActivityMapper.addActivityUser(GenerateUUID.create(),uid,activityId,name,phone,new Date());
                    return true;
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }catch (Exception e){
            throw new Exception();
        }finally {
            //释放lock锁
            lock.unlock();
        }
    }


    /**
     * 查询有活动的城市
     * @return
     */
    @Override
    public List<City> findActivityCity() {
        return superActivityMapper.findActivityCity();
    }


    /**
     * 通过城市id查询有活动的区域
     * @param cityId
     * @return
     */
    @Override
    public List<Area> findActivityAreaByCityId(String cityId) {
        return superActivityMapper.findActivityArea(cityId);
    }
}
