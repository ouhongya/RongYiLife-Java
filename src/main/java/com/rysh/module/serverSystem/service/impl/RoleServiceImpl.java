package com.rysh.module.serverSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.*;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.serverSystem.mapper.RoleMapper;
import com.rysh.module.serverSystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AccountMapper accountMapper;

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 添加角色和对应的功能
     * @param role 角色
     * @return
     * @throws Exception
     */
    public boolean addRole( RoleAndFunIds role) throws Exception {
        //通过角色名称查询角色
        Role Trole=roleMapper.findRoleByRoleName(role.getRoleName());
        RoleAndFunction rf = new RoleAndFunction();
        if(Trole==null){
            Trole=new Role();
            //设置角色id  UUID
            Trole.setId(UUID.randomUUID().toString().toUpperCase().replace("-",""));
            //设置状态值
            Trole.setStatus(1);
            Trole.setRoleName(role.getRoleName());
            //设置创建时间
            Trole.setCreatedTime(new Date());
            //添加角色
            roleMapper.addRole(Trole);
            rf.setRid(Trole.getId());
            //功能查重
            Set<String> set=new HashSet<>();
            List<String> funs = role.getFunctions();
            List<Function> functions=new ArrayList<>();
            for (String id : funs) {
                set.add(id);
            }
            funs.clear();
            Function f=null;
            for (String s : set) {
                f=roleMapper.findFunctionByFid(s);
                functions.add(roleMapper.findFunctionByFid(s));
            }
            //遍历功能集合
            for (Function function : functions) {
                rf.setFid(function.getId());
                //添加功能
                roleMapper.addFunctionToRole(rf);
            }
            return true;
        }else {
            //角色不为null  说明角色已经存在
            return false;
        }
    }

    /**
     * 删除角色  该角色必须在没有绑定用户的情况下
     * @param
     * @return
     * @throws Exception
     */
    public boolean deleteRole(String roleId) throws Exception {

        List<User> users=roleMapper.findUserByRoleId(roleId);
        if(users.size()==0||users==null){
            //通过角色名称查询角色
            Role role =  roleMapper.findRoleByRid(roleId);

            //删除角色所绑定的功能
            roleMapper.deleteFunctionByRole(role.getId());
            //删除角色
            roleMapper.deleteRole(role.getRoleName());
            return true;
        }else {
            return false;
        }

    }

    /**
     * 编辑角色
     * @param role  角色对象
     * @param
     */
    public Boolean updateRole(Role role) throws Exception {
        //查询角色名称是否有重复的
        Role ro = roleMapper.findRoleByRoleName(role.getRoleName());
        if(ro==null||role.getId().equals(ro.getId())){
            //修改角色名
            roleMapper.updateRoleName(role);
            //通过角色id查询对应的功能
            List<Function> oldFunctions = roleMapper.findFunctionByRoleId(role.getId());
            //将所有oldfunctionurl存入一个集合
            List<String> oldUrls=new ArrayList<>();
            for (Function oldFunction : oldFunctions) {
                oldUrls.add(oldFunction.getFunctionUrl());
            }
            //将所有newfunctionurl存入一个集合
            List<String> newUrls=new ArrayList<>();
            List<Function> newFunctions = role.getFunctions();
            Set<String> set=new HashSet<>();
            for (Function newFunction : newFunctions) {
                set.add(newFunction.getId());
            }
            newFunctions.clear();
            for (String s : set) {
                newFunctions.add(roleMapper.findFunctionByFid(s));
            }
            for (Function newFunction : newFunctions) {
                newUrls.add(newFunction.getFunctionUrl());
            }
            for (String oldUrl : oldUrls) {
                //如果新集合中不包含老集合的功能  那说明是删除
                if(!newUrls.contains(oldUrl)){
                    deleteFunFromRole(role.getRoleName(),oldUrl);
                }
            }
            for (String newUrl : newUrls) {
                //如果老集合中不包含新集合的功能  那说明是增加
                if(!oldUrls.contains(newUrl)){
                    addFunFromRole(role.getRoleName(),newUrl);
                }
            }

            return true;
        }else {
            return false;
        }


    }

    /**
     * 从角色中移除功能
     *
     * @param roleStr
     * @param
     * @return
     */
    private boolean deleteFunFromRole(String roleStr, String url) throws Exception {
        //通过角色名称查询用户
        List<User> users = roleMapper.findUserByRole(roleStr);
        //遍历用户集合 查询每个用户的所有角色
        for (User user : users) {
            List<Role> roles = roleMapper.findRoleByUid(user.getId());
            //遍历角色集合 过滤掉当前角色
            for (Role role : roles) {
                if(roleStr.equals(role.getRoleName())){
                    if(roles.size()==1&&role.getRoleName().equals(roleStr)){
                        UserAndFunction uf = new UserAndFunction();
                        uf.setUid(user.getId());
                        uf.setFid(roleMapper.findFidByUrl(url));
                        roleMapper.deleteUserFunction(uf);
                    }
                    continue;
                }else {
                    //查询角色所包含的功能集合
                    List<Function> fun = roleMapper.findFunctionByRoleId(role.getId());
                    //取出url 存入一个新的集合
                    List<String> funUrl=new ArrayList<>();
                    for (Function function : fun) {
                        funUrl.add(function.getFunctionUrl());
                    }
                    //判断该用户拥有的其他角色是否包含该功能
                    if(!funUrl.contains(url)){
                        UserAndFunction uf = new UserAndFunction();
                        uf.setUid(user.getId());
                        uf.setFid(roleMapper.findFidByUrl(url));
                        roleMapper.deleteUserFunction(uf);
                    }

                }
            }
        }

        //再从角色中删除功能
        RoleAndFunction rf = new RoleAndFunction();
        rf.setFid(roleMapper.findFidByUrl(url));
        rf.setRid(roleMapper.findRidByroleName(roleStr));
        roleMapper.deleteFunctionFromRole(rf);
        return true;
    }

    /**
     * 为角色添加权限，并为角色关联的用户添加角色
     * @param roleStr  角色名称
     * @param
     * @return
     */
    //在角色上增加权限
    private boolean addFunFromRole(String roleStr, String url) throws Exception {
        //通过角色查询用户
        List<User> users = roleMapper.findUserByRole(roleStr);
        //遍历用户集合
        for (User user : users) {
            //查询用户对应的所有功能
            List<Function> functions = roleMapper.findFunctionByUsername(user.getUsername());
            //将所有功能的url存入一个新的集合
            List<String> urls=new ArrayList<>();
            for (Function function : functions) {
                urls.add(function.getFunctionUrl());
            }
            if(!urls.contains(url)){
                //给用户添加功能
                UserAndFunction uf = new UserAndFunction();
                uf.setUid(user.getId());
                uf.setFid(roleMapper.findFidByUrl(url));
                roleMapper.addFunctionToUser(uf);
            }
        }
        //给角色添加功能
        RoleAndFunction rf = new RoleAndFunction();
        rf.setRid(roleMapper.findRidByroleName(roleStr));
        rf.setFid(roleMapper.findFidByUrl(url));
        roleMapper.addFunctionToRole(rf);
        return true;
    }


    /**
     * 查询所有功能  并排序
     * @return
     * @throws Exception
     */
    public List<ParentFandSonF> findAllFunction() throws Exception{
        //查询所有一级菜单  并降序排列
        List<Function> parentFuncs=accountMapper.findAllParentFunc();
        //一级二级菜单组合类集合
        List<ParentFandSonF> pf=new ArrayList<>();
        //遍历一级菜单
        for (Function parentFunc : parentFuncs) {
            //为每个一级菜单创建一个ParentFandSonF对象
            ParentFandSonF parentFandSonF = new ParentFandSonF();
            parentFandSonF.setId(accountMapper.findFidByFunctionName(parentFunc.getFunctionName()));
            //设置一级菜单名称
            parentFandSonF.setParentFunctionName(parentFunc.getFunctionName());

            //为所有二级菜单设置对应的一级菜单名称
            List<Function> sonFuncByParentId = accountMapper.findSonFuncByParentId(parentFunc.getId());
            for (Function function : sonFuncByParentId) {
                function.setParentFunctionName(parentFunc.getFunctionName());
            }
            //为一级菜单 设置二级菜单
            parentFandSonF.setFunctions(sonFuncByParentId);

            pf.add(parentFandSonF);
        }
        return pf;

    }


    /**
     * 查询所有角色和角色对应的功能
     * @return
     * @throws Exception
     */
    public PageInfo<Role> findAllRole(ParamBean paramBean) throws Exception{

        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled() + " "+paramBean.getSortByOrder());
        List<Role> roles=roleMapper.findAllRole();
        for (Role role : roles) {
            role.setCreateTimeStr(sf.format(role.getCreatedTime()));
            role.setFunctions(roleMapper.findFunctionByRole(role.getRoleName()));
        }
        PageInfo<Role> PageInfo = new PageInfo<>(roles);
        return PageInfo;
    }

    /**
     * 通过Rid查询角色
     * @param roldId
     * @return
     * @throws Exception
     */
    public RoleAndAllFunction findRoleByRid(String roldId) throws Exception {
        //通过Rid查询角色
        Role role = roleMapper.findRoleByRid(roldId);
        //通过Rid查询角色对应的一级菜单集合
        List<Function> funList=roleMapper.findParentFunctionByRid(roldId);
        //一级菜单二级菜单组合类集合
        List<ParentFandSonF> pf=new ArrayList<>();
        //遍历一级菜单集合 为每个一级菜单创建一个ParentFandSonF对象
        for (Function function : funList) {
            ParentFandSonF parentFandSonF = new ParentFandSonF();
            //设置一级菜单id
            parentFandSonF.setId(function.getId());
            //设置一级菜单名称
            parentFandSonF.setParentFunctionName(function.getFunctionName());
            //为所有一级菜单设置对应的二级菜单 并按order_num升序排列
            parentFandSonF.setFunctions(accountMapper.findSonFuncByParentId(function.getId()));

            pf.add(parentFandSonF);
        }


        role.setPs(pf);
        RoleAndAllFunction roleAndAllFunction = new RoleAndAllFunction();
        roleAndAllFunction.setRole(role);

        roleAndAllFunction.setPs(findAllFunction());
        return roleAndAllFunction;

    }

    /**
     * 模糊查询 通过条件查询角色
     * @param paramBean
     * @return
     */
    public PageInfo<Role> searchAllRole(ParamBean paramBean) throws Exception {
        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<Role> roles=roleMapper.searchAllRole(paramBean.getSearch());
        for (Role role : roles) {
            role.setCreateTimeStr(sf.format(role.getCreatedTime()));
            role.setFunctions(roleMapper.findFunctionByRole(role.getRoleName()));
        }
        PageInfo<Role> rolePageInfo = new PageInfo<>(roles);
        return rolePageInfo;
    }
}

