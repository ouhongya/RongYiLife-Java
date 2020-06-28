package com.rysh.module.serverSystem.service;

import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.ParentFandSonF;
import com.rysh.module.serverSystem.beans.Role;
import com.rysh.module.serverSystem.beans.RoleAndAllFunction;
import com.rysh.module.serverSystem.beans.RoleAndFunIds;

import java.util.List;

public interface RoleService {
//添加角色和对应的功能  true 添加成功  false  添加失败 角色名称已存在
    boolean addRole( RoleAndFunIds role) throws Exception;
//删除角色  该角色必须在没有绑定用户的情况下
    boolean deleteRole(String roleId) throws Exception;
//编辑角色  true 编辑成功 false 编辑失败 角色名称重复
    Boolean updateRole(Role role) throws Exception;
//查询所有功能  并排序
    List<ParentFandSonF> findAllFunction() throws Exception;
//查询所有角色和角色对应的功能
    PageInfo<Role> findAllRole(ParamBean paramBean) throws Exception;
//通过Rid查询角色
    RoleAndAllFunction findRoleByRid(String roldId) throws Exception;
//模糊查询 通过条件查询角色
    PageInfo<Role> searchAllRole(ParamBean paramBean) throws Exception;
}
