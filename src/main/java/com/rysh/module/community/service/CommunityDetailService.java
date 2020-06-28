package com.rysh.module.community.service;

import com.rysh.module.community.beans.CommunityDetail;

public interface CommunityDetailService {
    public boolean addCommunityDetails(CommunityDetail communityDetails);
    public String getID(CommunityDetail communityDetail);
}
