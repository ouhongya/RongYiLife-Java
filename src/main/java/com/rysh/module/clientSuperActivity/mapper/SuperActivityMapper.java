package com.rysh.module.clientSuperActivity.mapper;

import com.rysh.module.activity.beans.Activity;
import com.rysh.module.activity.beans.ActivityContent;
import com.rysh.module.activity.beans.ActivityVo;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface SuperActivityMapper {
    List<ActivityVo> findSuperActivity(@Param("search") String search, @Param("cityId") String cityId, @Param("areaId") String areaId);

    String findActivityCoverImg(String id);

    List<String> findActivityBannerImgs(String id);


    void addActivityUser(@Param("id") String id, @Param("uid") String uid, @Param("activityId") String activityId, @Param("name") String name, @Param("phone") String phone, @Param("date") Date date);

    String findActivityUser(@Param("uid") String uid, @Param("activityId") String activityId);

    List<City> findActivityCity();

    List<Area> findActivityArea(String cityId);

    ActivityVo findActivityById(String activityId);

    Integer countActivityUserByActivityId(String activityId);

    List<ActivityContent> findActivityContentByActivityId(String id);
}
