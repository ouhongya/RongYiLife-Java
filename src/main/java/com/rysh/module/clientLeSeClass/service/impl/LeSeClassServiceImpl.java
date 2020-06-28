package com.rysh.module.clientLeSeClass.service.impl;

import com.rysh.module.clientLeSeClass.beans.LeSe;
import com.rysh.module.clientLeSeClass.beans.SuperLeSe;
import com.rysh.module.clientLeSeClass.service.LeSeClassService;
import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.clientLoginRegister.mapper.LoginAndRegisterMapper;
import com.rysh.module.community.beans.Area;
import com.rysh.module.community.beans.City;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.mapper.AreaMapper;
import com.rysh.module.community.mapper.CityMapper;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.garbage.beans.Garbage;
import com.rysh.module.garbage.beans.GarbageCategory;
import com.rysh.module.garbage.mapper.GarbageCategoryMapper;
import com.rysh.module.garbage.mapper.GarbageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeSeClassServiceImpl implements LeSeClassService {
    @Autowired
    private LoginAndRegisterMapper loginAndRegisterMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private GarbageCategoryMapper garbageCategoryMapper;

    @Autowired
    private GarbageMapper garbageMapper;

    /**
     * 查询垃圾类别
    * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public LeSe findLeSeCategoryByToken(String uid) throws Exception {
        //通过uid获取用户信息
        User user = loginAndRegisterMapper.findNowUser(uid);
        //通过小区id获取用户绑定的小区信息
        Community community = communityMapper.findCommunityById(user.getCommunityId());
        //通过区域id查询用户小区所在的区域
        Area area = areaMapper.findAreaById(community.getAreaId());
        //通过城市id查询用户区域所在的城市
        City city = cityMapper.findCityById(area.getCityId());
        //通过城市id查询垃圾类别
        List<GarbageCategory> garbageCategories=garbageCategoryMapper.findGarbageCategoryByCityId(city.getId());

        LeSe leSe = new LeSe();
        leSe.setId(city.getId());
        leSe.setName(city.getCityName());
        leSe.setGarbageCategories(garbageCategories);
        return leSe;
    }


    /**
     * 通过垃圾分类id查询垃圾
     * @param categoryId
     * @return
     */
    @Override
    public List<Garbage> findLeSeByCategoryId(String categoryId) throws Exception {

        List<Garbage> garbages=garbageMapper.findGarbageByCategoryId(categoryId);

        return garbages;
    }

    @Override
    public List<SuperLeSe> searchLeSe(String search,String uid) {
        //查询用户绑定的小区所属的城市
        User user = loginAndRegisterMapper.findNowUser(uid);
        if(user!=null){
            Community community = communityMapper.findCommunityById(user.getCommunityId());
            Area area = areaMapper.findAreaById(community.getAreaId());
            City city = cityMapper.findCityById(area.getCityId());
            List<SuperLeSe> superLeSes=garbageMapper.searchLeSe(search,city.getId());
            return superLeSes;
        }else {
            return null;
        }

    }
}
