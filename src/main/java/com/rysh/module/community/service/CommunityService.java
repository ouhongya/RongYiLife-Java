package com.rysh.module.community.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.community.beans.ClientCommunity;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.beans.CommunityResponse;

import java.util.List;

public interface CommunityService {
    public boolean addCommunity(String areaName, String name, String address);

    public List<String> getAllCommunityByAreaName(String araName);

    public PageInfo<Community> getAllCommunity(int start, int size);

    public int updateCommunity(Community community);

    public CommunityResponse findCommunityById(String id);

    public int communityAdd(Community community);

    public int deleteCommunity(String communityId);

    List<ClientCommunity> getAllCommunityByAreaId(String areaId);
}
