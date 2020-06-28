package com.rysh.module.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.community.beans.ClientCommunity;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.beans.CommunityResponse;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.community.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private AreaServiceImpl areaService;

    public boolean addCommunity(String areaName, String name, String address) {
        //查询areaID
        String areaId = areaService.getAreaId(areaName);
        if (areaId == null){
            return false;
        }
        Community community = new Community() ;
        community.setName(name);
        community.setCreatedTime( new Timestamp(System.currentTimeMillis()) );
        community.setAreaId(areaId);
        community.setAddress(address);

        int status = communityMapper.addCommunity(community);
        return status == 1 ? true :false;
    }

    public List<String> getAllCommunityByAreaName(String araName){
        return communityMapper.findAllByAreaName(araName);
    }

    public PageInfo<Community> getAllCommunity(int start,int size){
        PageHelper.startPage(start,size);
        List<Community> all = communityMapper.findAll();
        PageInfo<Community> pageInfo = new PageInfo<>(all);
        return pageInfo;
    }

    public int updateCommunity(Community community) {
        return communityMapper.updateCommunity(community);
    }

    public CommunityResponse findCommunityById(String id) {
        return communityMapper.findById(id);
    }

    public int communityAdd(Community community) {
        community.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        return communityMapper.insertCommunity(community);
    }

    public int deleteCommunity(String communityId) {
       return communityMapper.deleteCommunity(communityId);
    }

    @Override
    public List<ClientCommunity> getAllCommunityByAreaId(String areaId) {
        return communityMapper.findAllByAreaId(areaId);
    }
}
