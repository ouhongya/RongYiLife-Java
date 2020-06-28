package com.rysh.module.serverSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.farm.mapper.FarmMapper;
import com.rysh.module.grange.beans.Grange;
import com.rysh.module.grange.mapper.GrangeMapper;
import com.rysh.module.serverSystem.beans.*;
import com.rysh.module.serverSystem.mapper.AccountMapper;
import com.rysh.module.serverSystem.mapper.RoleMapper;
import com.rysh.module.serverSystem.mapper.StoreMapper;
import com.rysh.module.serverSystem.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
   private AccountMapper userMapper;

    @Autowired
   private RoleMapper roleMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private FarmMapper farmMapper;

    @Autowired
    private GrangeMapper grangeMapper;

    private SimpleDateFormat sp=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Value("${result.tagId}")
    private String shopId;

    /**
     * 通过用户名查用户
     *
     * @param username 用户名
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        User user = userMapper.findUserByUsername(username);
        return user;
    }

    /**
     * 查询数据库中所有权限
     *
     * @return
     */
    public List<Function> findAllFunction() {
        List<Function> powerList = userMapper.findAllFunction();

        return powerList;
    }

    /**
     * 通过用户名查权限
     *
     * @param username 用户名
     * @return
     */
    public List<respUserFunc> findNowUser(String username) throws Exception {
        //查询所有一级菜单
        User user = userMapper.findUserByUsername(username);
        List<Function> parentFuns=userMapper.findParentFunctionByUserId(user.getId());   //所有一级菜单
        List<Function> funs = userMapper.findFunctionByUsername(username);  //所有菜单
        //功能集合
        List<respUserFunc> rps=new ArrayList<>();
        for (Function fun : parentFuns) {
            //当前用户拥有的功能类
            respUserFunc rp = new respUserFunc();
            rp.setName(fun.getFunctionUrl().replace("/",""));
            rp.setAlwaysShow(true);
            //一级url
            rp.setPath(fun.getFunctionUrl());
            //设置meta
            Meta meta = new Meta();
            meta.setTitle(fun.getFunctionName());

            rp.setMeta(meta);
            rp.setComponent("Layout");
            rp.setRedirect("");

            rps.add(rp);
        }

        //二级菜单信息

        for (respUserFunc rp : rps) {
            for (Function fun : funs) {
                if(rp.getMeta().getTitle().equals(userMapper.findParentFunctionNameByFunctionId(fun.getId()))){
                    Children c=new Children();
                    //设置二级Component
                    c.setComponent(fun.getFunctionUrl());
                    //设置二级名称
                    c.setName(fun.getFunctionUrl().replace("/",""));
                    //设置二级url
                    c.setPath(fun.getFunctionUrl());
                    //设置二级的meta
                    Meta meta = new Meta();
                    meta.setTitle(fun.getFunctionName());

                    c.setMeta(meta);
                    rp.getChildren().add(c);
                }
            }

        }


        return rps;
    }

    /**
     * 添加用户和权限
     *
     * @param user
     * @param
     * @return
     */
    public boolean addUser(User user) throws Exception  {
        User isuser = userMapper.findUserByUsername(user.getUsername());
        if(isuser==null){

            //为user设置id
            user.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            //状态码   是否启用
            user.setStatus(1);

            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
            //设置创建时间
            user.setCreatedTime(new Date());
            //添加用户
            userMapper.addUser(user);
            //绑定用户和角色
            //userid和roleid的组合类
            Set<String> set=new HashSet<>();
            List<Role> roles = user.getRoles();
            for (Role role : roles) {
                set.add(role.getId());
            }
            roles.clear();
            for (String s : set) {
                roles.add(roleMapper.findRoleByRid(s));
            }
            UserAndRole ur = new UserAndRole();
            for (Role role : roles) {
                ur.setUid(user.getId());
                ur.setRid(userMapper.findRidByroleName(role.getRoleName()));
                userMapper.addRoleByUid(ur);
            }
            //绑定用户和权限
            //userid和function的组合类

            UserAndFunction uf = new UserAndFunction();
            //功能集合
            ArrayList<String> fidList = new ArrayList<>();
            String fid = "";
            List<Function> functions = user.getFunctions();
            Set<String> funset = new HashSet();
            for (int i = 0; i < functions.size(); i++) {
                if(functions.get(i)==null){
                    functions.remove(i);
                    i--;
                }
            }
            for (Function function : functions) {
                funset.add(function.getId());
            }

            functions.clear();
            for (String s : funset) {
                functions.add(roleMapper.findFunctionByFid(s));
            }
            for (Function function : functions) {
                //通过功能名称查询功能id
                fid=userMapper.findFidByFunctionUrl(function.getFunctionUrl());
                //去重
                if (!fidList.contains(fid)) {
                    fidList.add(fid);
                }
            }
            for (String f : fidList) {
                uf.setUid(user.getId());
                uf.setFid(f);
                userMapper.addFunctionByUid(uf);
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 通过角色查用户
     *
     * @param role
     * @return
     */
    public PageInfo<User> findUserByRole(String role,String pageNum,String pageSize) throws Exception {
        int pageN = Integer.parseInt(pageNum);
        int pageS = Integer.parseInt(pageSize);
        if(pageN==0){
            pageN=1;
        }
        if(pageS==0){
            pageS=5;
        }
        PageHelper.startPage(pageN,pageS);
        List<User> users = userMapper.findUserByRole(role);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    /**
     * 通过id查角色
     *
     * @param id
     * @return
     */
    public List<Role> findRoleById(String id) {
        return userMapper.findRoleById(id);
    }



    /**
     * 查询用户的角色功能
     * @param username 用户名
     * @return
     * @throws Exception
     */
    public RoleStrAndFunctionStr findRoleFunctionByUsername(String username) throws Exception {
        //通过用户名查询用户
        User user = userMapper.findUserByUsername(username);
        //通过用户id查询角色
        List<Role> roles = userMapper.findRoleByUid(user.getId());
        //通过用户名查询功能集
        List<Function> functions = userMapper.findFunctionByUsername(username);
        //创建角色功能组合类
        RoleStrAndFunctionStr rf = new RoleStrAndFunctionStr();
        rf.setRoles(roles);
        rf.setFunctions(functions);
        return rf;
    }


    /**
     *  查询所有用户和对应的角色
     * @return
     * @throws Exception
     */
    public PageInfo<User> findAllUser(ParamBean paramBean) throws Exception {

        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        //分页
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<User> users=userMapper.findAllUser();

        for (int i = 0; i < users.size(); i++) {
            //设置角色和时间字符串
            users.get(i).setRoles(userMapper.findRoleByUid(users.get(i).getId()));
            users.get(i).setFunctions(userMapper.findFunctionByUid(users.get(i).getId()));
            users.get(i).setCreatedTimeStr(sp.format(users.get(i).getCreatedTime()));
            users.get(i).setLastedUpdateTimeStr(sp.format(users.get(i).getLastedUpdateTime()));
        }
        PageInfo<User> pageinfo = new PageInfo<>(users);
        return pageinfo;
    }

    /**
     * 删除用户，包括对应的角色和功能
     * @param userid
     */
    public Boolean deleteUserByUserId(String userid) throws Exception {
        //判断用户是否删除的是自己
        if(userMapper.findUserById(userid).getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return false;
        }else {
            //查询用户关联的所有角色
            List<Role> roles = userMapper.findRoleByUid(userid);
            UserAndRole ur = new UserAndRole();
            //删除角色
            ur.setUid(userid);
            for (Role role : roles) {
                ur.setRid(role.getId());
                userMapper.deleteRoleFromUser(ur);
            }
            //查询用户关联的所有功能
            List<Function> functions=userMapper.findFunctionByUid(userid);
            UserAndFunction uf = new UserAndFunction();
            uf.setUid(userid);
            //删除功能
            for (Function function : functions) {
                uf.setFid(function.getId());
                userMapper.deleteFunctionFromUser(uf);
            }
            //如果是外部用户 删除用户跟第三方平台的关系
            if(userMapper.findUserById(userid).getIsInsider()!=0){
                userMapper.updateUserCompanyId(userid);
            }
            //将用户软删除
            userMapper.deleteUser(userid);
            return true;
        }
    }


    //删除用户功能和角色
    private void DeleteUserFRByUserId(String userId) throws Exception{
        //查询用户关联的所有角色
        List<Role> roles = userMapper.findRoleByUid(userId);
        UserAndRole ur = new UserAndRole();
        //删除角色
        ur.setUid(userId);
        for (Role role : roles) {
            ur.setRid(role.getId());
            userMapper.deleteRoleFromUser(ur);
        }
        //查询用户关联的所有功能
        List<Function> functions=userMapper.findFunctionByUid(userId);
        UserAndFunction uf = new UserAndFunction();
        uf.setUid(userId);
        //删除功能
        for (Function function : functions) {
            uf.setFid(function.getId());
            userMapper.deleteFunctionFromUser(uf);
        }
    }

    /**
     * 查询所有角色和功能
     * @return
     */
    public RoleAndAllFunction findAllRoleAndFunction() throws Exception {

        //查询所有角色
        List<Role> roles=userMapper.findAllRole();
        //通过角色查询对应的功能
        for (Role role : roles) {
            List<ParentFandSonF> ps=new ArrayList<>();
           //查询角色对应的一级菜单 并排序
            List<Function> Pfunctions = userMapper.findParentFunctionByRoleName(role.getRoleName());
            //遍历查询角色对应的所有二级菜单  并为他们设置一级菜单   排序
            List<Function> Sfunctions = userMapper.findFunctionByRole(role.getRoleName());
            //一级菜单名称
            String parentFuncName="";
            for (Function sfunction : Sfunctions) {
                parentFuncName=userMapper.findParentFunctionNameByFunctionId(sfunction.getId());
                if(parentFuncName==null||"".equals(parentFuncName)){
                    continue;
                }else {
                    sfunction.setParentFunctionName(parentFuncName);
                }
            }
            //为每个一级菜单创建一个ParentFandSonF对象
            for (Function pfunction : Pfunctions) {
                ParentFandSonF parentFandSonF = new ParentFandSonF();
                parentFandSonF.setParentFunctionName(pfunction.getFunctionName());
                parentFandSonF.setId(pfunction.getId());
                //设置一级菜单下的二级菜单
                for (Function sfunction : Sfunctions) {
                    if(parentFandSonF.getParentFunctionName().equals(sfunction.getParentFunctionName())){
                        parentFandSonF.getFunctions().add(sfunction);
                    }
                }
                ps.add(parentFandSonF);
            }

            role.setPs(ps);
        }

        //查询所有一级菜单  并降序排列
        List<Function> parentFuncs=userMapper.findAllParentFunc();
        //一级二级菜单组合类集合
        List<ParentFandSonF> pf=new ArrayList<>();
        //遍历一级菜单
        for (Function parentFunc : parentFuncs) {
            //为每个一级菜单创建一个ParentFandSonF对象
            ParentFandSonF parentFandSonF = new ParentFandSonF();
            parentFandSonF.setId(userMapper.findFidByFunctionName(parentFunc.getFunctionName()));
            //设置一级菜单名称
            parentFandSonF.setParentFunctionName(parentFunc.getFunctionName());

            //为所有二级菜单设置对应的一级菜单名称
            List<Function> sonFuncByParentId = userMapper.findSonFuncByParentId(parentFunc.getId());
            for (Function function : sonFuncByParentId) {
                function.setParentFunctionName(parentFunc.getFunctionName());
            }
            //为一级菜单 设置二级菜单
            parentFandSonF.setFunctions(sonFuncByParentId);

            pf.add(parentFandSonF);
        }

        //角色 功能组合类
        RoleAndAllFunction roleAndAllFunction = new RoleAndAllFunction();
        roleAndAllFunction.setRoles(roles);
        roleAndAllFunction.setPs(pf);
        //添加所有商铺
        List<Store> allStore = storeMapper.findAllStore();
        roleAndAllFunction.setStores(allStore);
        //添加所有农场
        List<FarmAndUser> allFarm = farmMapper.findAll();
        roleAndAllFunction.setFarmAndUsers(allFarm);
        //添加所有农庄
        List<Grange> granges = grangeMapper.findAll();
        roleAndAllFunction.setGranges(granges);
        return roleAndAllFunction;
    }

    /**
     * 通过userid查询用户
     * @param uid
     * @return
     * @throws Exception
     */
    public UserAndAllRoleFunction findUserByUid(String uid) throws Exception{
        UserAndAllRoleFunction urf = new UserAndAllRoleFunction();
        //通过uid查询用户
        User user = userMapper.findUserById(uid);
        //通过uid查询角色
        List<Role> roles = userMapper.findRoleByUid(uid);
        for (Role role : roles) {
            List<Function> functions = userMapper.findFunctionByRole(role.getRoleName());
            role.setFunctions(functions);
        }
        //通过uid查询功能
        List<Function> functions = userMapper.findFunctionByUid(uid);
        user.setRoles(roles);
        user.setFunctions(functions);
        urf.setUser(user);
        //查询所有角色功能
        List<Role> allRole = userMapper.findAllRole();
        for (Role role : allRole) {
            List<Function> functionByRole = userMapper.findFunctionByRole(role.getRoleName());
            role.setFunctions(functionByRole);
        }
        //如果是外部用户 就查询用户的农庄，农场，商铺
        String bossId=user.getCompanyId();
        urf.setRoles(allRole);
        urf.setBossId(bossId);
        return urf;

    }

    /**
     * 编辑用户
     * @param user
     * @throws Exception
     */
    public void updateUser(User user) throws Exception {
        //删除用户功能和角色 添加用户功能和角色
        DeleteUserFRByUserId(user.getId());
        //添加用户功能和角色
        addFunctionRoleToUser(user);
        //修改用户基本信息
        userMapper.updateUserBasicData(user);
    }

    /**
     * 重置密码
     * @param userid
     * @throws Exception
     */
    public void resetPassword(String userid) throws Exception {
        //查询用户 并判断用户是否存在或者是否被禁用
        User user = userMapper.findUserById(userid);
        if(user==null){

        }else {
            //用户存在
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode("123456");
            user.setPassword(password);
            //重置
            userMapper.updatePasswordByUserId(user);
        }

    }


    /**
     * 添加外部人员用户
     * @param user
     * @return
     */
    public String addNotInsiderUser(User user,String roleName,String bossId) throws Exception {
        User isuser = userMapper.findUserByUsername(user.getUsername());
        if(isuser==null){

            //为user设置id
            user.setId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            //状态码   是否启用
            user.setStatus(1);
            //密码加密
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(password);
            //设置创建时间
            user.setCreatedTime(new Date());
            if(user.getIsInsider()==1){
                //1 =====商铺
                //用户绑定商铺
                user.setCompanyId(bossId);
            }else if(user.getIsInsider()==2){
                //2=======农场
                //用户绑定农场
                user.setCompanyId(bossId);


            }else if(user.getIsInsider()==3){
                //3=====农庄
                //用户绑定农庄
                user.setCompanyId(bossId);
            }
            //添加用户
            userMapper.addUser(user);
            //绑定用户和角色
            //用户角色组合类
            UserAndRole ur = new UserAndRole();
            ur.setUid(user.getId());
            //用户功能组合类
            UserAndFunction uf = new UserAndFunction();
            uf.setUid(user.getId());
            Role role=roleMapper.findRoleByRoleName(roleName);
            //用户绑定角色
            ur.setRid(role.getId());
            userMapper.addRoleByUid(ur);
            //用户绑定功能
            List<Function> fun = roleMapper.findFunctionByRoleId(role.getId());
            for (Function function : fun) {
                uf.setFid(function.getId());
                roleMapper.addFunctionToUser(uf);
            }
            return "添加成功!";
        }else {
            return "该用户名已存在!";
        }
    }


    /**
     * 编辑外部用户
     * @param userr   用户基本信息
     * @return
     * @throws Exception
     */
    public String updateNotInsiderUser(newUser userr) throws Exception {
        //通过用户名查询用户
        User user = userMapper.findUserByUsername(userr.getUsername());
        user.setPhoneNum(userr.getPhoneNum());
        user.setUsername(userr.getUsername());
        user.setTrueName(userr.getTrueName());
        user.setIsInsider(userr.getIsInsider());
        user.setCompanyId(userr.getBossId());

        //修改用户基本信息
        userMapper.updateUserBasicData(user);
        //编辑用户角色
        //先删除角色和功能
        DeleteUserFRByUserId(user.getId());
        //添加用户功能和角色
        List<Role> roles=new ArrayList<>();
        roles.add(roleMapper.findRoleByRoleName(userr.getRoleName()));
        user.setRoles(roles);

        user.setFunctions(userMapper.findFunctionByRole(userr.getRoleName()));
        addFunctionRoleToUser(user);

        return "编辑成功";
    }

    private void addFunctionRoleToUser(User user) {
        //绑定用户和角色
        //userid和roleid的组合类
        Set<String> set = new HashSet<>();
        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            set.add(role.getId());
        }
        roles.clear();
        for (String s : set) {

            roles.add(roleMapper.findRoleByRid(s));
        }
        UserAndRole ur = new UserAndRole();
        for (Role role : roles) {
            ur.setUid(user.getId());
            ur.setRid(userMapper.findRidByroleName(role.getRoleName()));
            userMapper.addRoleByUid(ur);
        }
        //绑定用户和权限
        //userid和function的组合类

        UserAndFunction uf = new UserAndFunction();
        //功能集合
        ArrayList<String> fidList = new ArrayList<>();
        String fid = "";
        List<Function> functions = user.getFunctions();
        Set<String> funset = new HashSet();
        for (Function function : functions) {
            funset.add(function.getId());
        }
        functions.clear();
        for (String s : funset) {
            functions.add(roleMapper.findFunctionByFid(s));
        }
        for (Function function : functions) {
            //通过功能名称查询功能id
            fid = userMapper.findFidByFunctionUrl(function.getFunctionUrl());
            //去重
            if (!fidList.contains(fid)) {
                fidList.add(fid);
            }
        }
        for (String f : fidList) {
            uf.setUid(user.getId());
            uf.setFid(f);
            userMapper.addFunctionByUid(uf);
        }
    }

    /**
     * 通过条件模糊查询所有用户
     * @param paramBean   当前页
     * @return
     */
    public PageInfo<User> searchAllUser(ParamBean paramBean) throws Exception {
        //判断 如果是第0页 就变成第1页
        if(paramBean.getPage()<=0){
            paramBean.setPage(1);
        }
        if(paramBean.getSize()<=0){
            paramBean.setSize(5);
        }
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize());
        PageHelper.orderBy(paramBean.getSortByFiled()+" "+paramBean.getSortByOrder());
        List<User> users=userMapper.searchAllUser(paramBean.getSearch());
        for (int i = 0; i < users.size(); i++) {
            //设置角色和时间字符串
            users.get(i).setRoles(userMapper.findRoleByUid(users.get(i).getId()));
            users.get(i).setFunctions(userMapper.findFunctionByUid(users.get(i).getId()));
            users.get(i).setCreatedTimeStr(sp.format(users.get(i).getCreatedTime()));
            users.get(i).setLastedUpdateTimeStr(sp.format(users.get(i).getLastedUpdateTime()));
        }
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        return userPageInfo;
    }

    /**
     * 修改密码
     * @param userId   用户id
     * @param oldPassword   旧密码
     * @param newPassword  新密码
     */
    public Boolean updatePassword(String userId, String oldPassword,String newPassword) throws Exception {
        //通过userid查询用户
        User u = userMapper.findUserById(userId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //判断用户输入的旧密码是否正确
        if(bCryptPasswordEncoder.matches(oldPassword,u.getPassword())){
            //输入正确  修改密码
            User user = new User();
            user.setId(userId);
            String BCnewPassword = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(BCnewPassword);
            userMapper.updatePasswordByUserId(user);
            return true;
        }else {
            return false;
        }
    }

}



