package com.rysh.module.clientSuperActivity.service;

import com.rysh.module.clientSuperActivity.beans.ActivityPlus;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;

import java.util.List;

public interface SuperActivityService {
    List<ActivityPlus> findSuperActivity(String uid, ParamBean paramBean, String cityId, String areaId);

    boolean activitySignUp(String uid, String activityId,String name,String phone) throws Exception;

    List<City> findActivityCity();

    List<Area> findActivityAreaByCityId(String cityId);
}
