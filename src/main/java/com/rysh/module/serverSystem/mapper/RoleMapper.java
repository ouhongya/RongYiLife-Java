package com.rysh.module.serverSystem.mapper;

import com.rysh.module.serverSystem.beans.*;

import java.util.List;

public interface RoleMapper {
    Role findRoleByRoleName(String roleName);

    void addRole(Role role);

    void addFunctionToRole(RoleAndFunction rf);

    List<User> findUserByRole(String roleName);

    void deleteRole(String roleName);

    List<Function> findFunctionByRoleId(String roleId);

    String findRidByroleName(String roleStr);

    void deleteFunctionFromRole(RoleAndFunction rf);

    List<Role> findRoleByUid(String id);

    List<Function> findFunctionByRole(String roleName);

    void deleteUserFunction(UserAndFunction uf);

    List<Function> findFunctionByUsername(String username);

    void addFunctionToUser(UserAndFunction uf);

    Role findRoleByRid(String id);

    void updateRoleName(Role role);

    void deleteFunctionByRole(String id);

    String findFidByUrl(String s);

    List<Role> findAllRole();

    Function findFunctionByFid(String funId);

    List<User> findUserByRoleId(String roleId);

    List<Role> searchAllRole(String searchCondition);

    List<Function> findParentFunctionByRid(String roldId);
}
