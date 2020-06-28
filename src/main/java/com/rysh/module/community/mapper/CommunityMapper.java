package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.ClientCommunity;
import com.rysh.module.community.beans.Community;
import com.rysh.module.community.beans.CommunityResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommunityMapper {
    int addCommunity(Community community);

    List<String> findAllByAreaName(String areaName);

    String findUserCommunityByAddressId(String addressId);

    List<Community> findAll();

    int updateCommunity(Community community);

    CommunityResponse findById(@Param("id") String id);

    int insertCommunity(Community community);

    int deleteCommunity(String communityId);

    Community findCommunityById(String id);

    List<ClientCommunity> findAllByAreaId(String areaId);
}
