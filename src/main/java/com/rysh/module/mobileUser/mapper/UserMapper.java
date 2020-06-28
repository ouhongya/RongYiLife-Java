package com.rysh.module.mobileUser.mapper;

import com.rysh.module.clientLoginRegister.beans.User;
import com.rysh.module.mobileUser.beans.UserIdAndScore;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

     User findById(String id);

    void updateUserScore(UserIdAndScore us);
}
