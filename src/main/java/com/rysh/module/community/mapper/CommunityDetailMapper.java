package com.rysh.module.community.mapper;

import com.rysh.module.community.beans.CommunityDetail;

public interface CommunityDetailMapper {
    int addCommunityDetail(CommunityDetail communityDetail);

    int checkHasExists(CommunityDetail communityDetails);

    String getId(CommunityDetail communityDetail);

    CommunityDetail getHouseNumberById(String addressId);
}
