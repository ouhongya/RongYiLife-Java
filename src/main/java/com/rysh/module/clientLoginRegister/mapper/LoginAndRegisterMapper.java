package com.rysh.module.clientLoginRegister.mapper;

import com.rysh.module.clientLoginRegister.beans.User;
import org.apache.ibatis.annotations.Param;

public interface LoginAndRegisterMapper {
     User findUserByPhoneNum(String phoneNum);

     void addUser(User user);

    User findNowUser(String uid);

    void updateUserById(User user);

    /**
     * 查询当前用户所处的城市
     * @param userId
     * @return java.lang.String
     * @author Hsiang Sun
     * @date 2019/10/23 14:42
     */
    String findCityIdByUserId(String userId);

    void updateUserAvatar(@Param("uid") String uid, @Param("url") String url);
}
