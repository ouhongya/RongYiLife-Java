package com.rysh.module.community.service.impl;

import com.rysh.module.community.beans.CommunityDetail;
import com.rysh.module.community.mapper.CommunityDetailMapper;
import com.rysh.module.community.service.CommunityDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CommunityDetailServiceImpl implements CommunityDetailService {

    @Autowired
    private CommunityDetailMapper communityDetailMapper;

    public boolean addCommunityDetails(CommunityDetail communityDetails){

        //判断当前户号是否已经被使用
        int num = communityDetailMapper.checkHasExists(communityDetails);
        if (num  > 0 ){
            return false;
        }

        communityDetails.setCreatedTime(new Timestamp(System.currentTimeMillis() ));
        int status = communityDetailMapper.addCommunityDetail(communityDetails);
        return status == 1 ? true : false ;

    }

    public String getID(CommunityDetail communityDetail) {
        return communityDetailMapper.getId(communityDetail);
    }
}
