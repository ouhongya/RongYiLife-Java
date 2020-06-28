package com.rysh.module.serverSystem.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.*;
import java.util.List;

public interface AccountService {
//通过用户名查询用户
    User findUserByUsername(String username) throws Exception;
//查询所有的功能
    List<Function> findAllFunction();
//通过用户名查询该用户拥有的功能
    List<respUserFunc> findNowUser(String username) throws Exception;
//添加用户   true  添加成功  false 用户名称重复
    boolean addUser(User user) throws Exception;
//通过角色查询用户
    PageInfo<User> findUserByRole(String role, String pageNum, String pageSize) throws Exception;
//通过id查角色
    List<Role> findRoleById(String id);
//查询用户的角色功能
    RoleStrAndFunctionStr findRoleFunctionByUsername(String username) throws Exception;
//查询所有用户和对应的角色
    PageInfo<User> findAllUser(ParamBean paramBean) throws Exception;
//删除用户，包括对应的角色和功能    true 删除成功  false  删除的人是自己  不能删除
    Boolean deleteUserByUserId(String userid) throws Exception;
//查询所有角色和功能
    RoleAndAllFunction findAllRoleAndFunction() throws Exception;
//通过userid查询用户
    UserAndAllRoleFunction findUserByUid(String uid) throws Exception;
//编辑用户
    void updateUser(User user) throws Exception;
//重置密码为123456
    void resetPassword(String userid) throws Exception;
//添加外部人员用户
    String addNotInsiderUser(User user,String roleName,String bossId) throws Exception;
//编辑外部用户
    String updateNotInsiderUser(newUser userr) throws Exception;
//通过条件模糊查询所有用户
    PageInfo<User> searchAllUser(ParamBean paramBean) throws Exception;
//修改密码
    Boolean updatePassword(String userId, String oldPassword,String newPassword) throws Exception;



}
