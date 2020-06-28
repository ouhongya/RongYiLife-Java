package com.rysh.module.mobileUser.service.user;

import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.community.beans.CommunityDetail;
import com.rysh.module.community.mapper.AreaMapper;
import com.rysh.module.community.mapper.CityMapper;
import com.rysh.module.community.mapper.CommunityDetailMapper;
import com.rysh.module.community.mapper.CommunityMapper;
import com.rysh.module.mobileUser.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private CommunityDetailMapper communityDetailMapper;

    public User findById(String id){
       return userMapper.findById(id);
    }







    public CommunityDetail getHouseNumber(String addressId) {
        return communityDetailMapper.getHouseNumberById(addressId);
    }

    public String getUserCity(String addressId) {
        return cityMapper.getUserCityByUserAddress(addressId);
    }

    public String getUserArea(String addressId) {
        return areaMapper.findUserAreaByAddressId(addressId);
    }

    public String getUserCommunity(String addressId) {
        return communityMapper.findUserCommunityByAddressId(addressId);
    }

}
