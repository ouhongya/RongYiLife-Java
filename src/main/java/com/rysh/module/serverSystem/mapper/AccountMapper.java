package com.rysh.module.serverSystem.mapper;

import com.rysh.module.serverSystem.beans.*;

import java.util.List;

public interface AccountMapper {

    User findUserByUsername(String username);

    List<Function> findAllFunction();

    List<Function> findFunctionByUsername(String username);

    List<Function> findFunctionByUserNameForPower(String username);

    void addUser(User user);

    void addFunctionByUid(UserAndFunction uf);

    List<User> findUserByRole(String role);

    void addRoleByUid(UserAndRole ur);

    String findFidByFunctionName(String s);

    String findRidByroleName(String s);

    List<Role> findRoleById(String id);

    List<Role> findRoleByUid(String userId);

    List<Function> findFunctionByRole(String roleName);

    void deleteUserFunction(UserAndFunction uf);

    void updateRoleName(OldRoleAndNewRole on);

    void addFunctionToUser(UserAndFunction uf);

    User findUserById(String userId);

    void deleteRoleFromUser(UserAndRole ur);

    List<User> findAllUser();

    List<Function> findFunctionByUid(String userid);

    List<Role> findAllRole();

    void deleteFunctionFromUser(UserAndFunction uf);

    void deleteUser(String userid);

    void updatePasswordByUserId(User user);

    void updateStatusByUserId(User user);

    String findFidByFunctionUrl(String functionUrl);

    String findParentFunctionNameByFunctionId(String id);

    void updateUserBasicData(User user);

    void updateOperator(OperatorAndStore os);

    List<Function> findAllParentFunc();

    List<Function> findSonFuncByParentId(String id);

    List<User> searchAllUser(String searchCondition);

    void updateUserCompanyId(String userid);

    List<Function> findParentFunctionByRoleName(String roleName);

    List<User> findUserByStoreId(String storeId);

    List<Function> findParentFunctionByUserId(String id);



}
