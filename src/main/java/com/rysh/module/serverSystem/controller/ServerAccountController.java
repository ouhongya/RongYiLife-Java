package com.rysh.module.serverSystem.controller;

import com.github.pagehelper.PageInfo;
import com.rysh.api.serverControllerApi.ServerUserControllerApi;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.*;
import com.rysh.module.serverSystem.mapper.RoleMapper;
import com.rysh.module.serverSystem.service.AccountService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Log4j2 
@RestController
@RequestMapping("/server/account")
class ServerAccountController implements ServerUserControllerApi {


    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleMapper roleMapper;

    @Value("${result.tagId}")
    private String shopId;

    @RequestMapping("/test")
    @ResponseBody
    public String sayHello() {
        return "test，欢迎来到中医农业研究院.";
    }



    /**
     * 添加角色和功能
     * @param userr
     * @param
     * @return
     */
    @PostMapping(value = "/addUser")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult addUser(@RequestBody newUser userr){
        QueryResult result = new QueryResult();
        try {
            User user = new User();
            user.setPassword(userr.getPassword());
            user.setPhoneNum(userr.getPhoneNum());
            user.setUsername(userr.getUsername());
            user.setTrueName(userr.getTrueName());
            user.setIsInsider(userr.getIsInsider());
            List<String> roleNameArr = userr.getRoleNameArr();
            List<Role> roles = new ArrayList<>();
            if(roleNameArr!=null&&roleNameArr.size()>0){
                for (String s : roleNameArr) {
                    roles.add(roleMapper.findRoleByRoleName(s));
                }
            }
            user.setRoles(roles);


            List<String> funIds = userr.getFunctionArr();
            List<Function> funs=new ArrayList<>();
            if(funIds!=null&&funIds.size()>0){
                for (String funId : funIds) {
                    funs.add(roleMapper.findFunctionByFid(funId));
                }
            }
            user.setFunctions(funs);
            if(userr.getIsInsider()!=0){
                String data= accountService.addNotInsiderUser(user,userr.getRoleName(),userr.getBossId());
                if(data.equals("添加成功!")){
                    result.setData(data);
                    return new QueryResponseResult(CommonCode.SUCCESS,result);
                }else {
                    result.setData(data);
                    return new QueryResponseResult(CommonCode.FAIL,result);
                }
            }else if(userr.getIsInsider()==0){
                if(userr.getFunctionArr()!=null&&userr.getFunctionArr().size()>0){
                    if(userr.getFunctionArr().contains("AACE73A52DDB4EE79A85112132247167")||
                            userr.getFunctionArr().contains("F7C12D285F7A461F89834FC015DEAB2E")||
                            userr.getFunctionArr().contains("99760B86873B4715BC44EBA5D5553A15")||
                            userr.getFunctionArr().contains("0AB2A73D5235414AA2B04AD002459F38")
                    ){
                        user.setCompanyId(shopId);
                    }
                }
                if(accountService.addUser(user)){
                    result.setData("添加成功");
                    return new QueryResponseResult(CommonCode.SUCCESS,result);
                }else {
                    result.setData("添加失败，用户名已存在！");
                    return new QueryResponseResult(CommonCode.FAIL,result);
                }
            }else {
                result.setData("添加失败，请选择用户归属地");
                return new QueryResponseResult(CommonCode.FAIL,result);
            }

        }catch (Exception e){
            e.printStackTrace();
            result.setData("添加用户异常");
            log.error(e);
            log.error("添加用户异常");
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }
    

    /**
     * 通过角色查用户
     * @param role
     * @return
     */
    @GetMapping(value = "/findUserByRole/{value}/pageNum/pageSize")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findUserByRole(@PathVariable(name = "value") String role, @PathVariable(name = "pageNum") String pageNum, @PathVariable(name = "pageSize") String pageSize ){
        QueryResult<Object> result = new QueryResult<>();
        PageInfo<User> pageInfo;
        try {
            pageInfo = accountService.findUserByRole(role,pageNum,pageSize);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过角色查询用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("查询异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
        return new QueryResponseResult(CommonCode.SUCCESS,result);
    }


    /**
     * 通过用户名查询角色和功能
     * @return
     */
    @GetMapping(value = "/findRoleFunctionByUsername/{value}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findRoleFunctionByUsername(){
        QueryResult<Object> result = new QueryResult<>();
        //获取当前登陆用户的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            RoleStrAndFunctionStr rf= accountService.findRoleFunctionByUsername(username);
            result.setData(rf);
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("通过用户名查询角色功能异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("通过用户名查询角色功能异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }
    

    /**
     * 查询所有用户
     * @param paramBean
     * @return
     */
    @GetMapping(value = "/findAllUser")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findAllUser(ParamBean paramBean){
        QueryResult<Object> result = new QueryResult<>();
        PageInfo<User> users = null;
        try {
            if(paramBean.getSearch()==null||"".equals(paramBean.getSearch())){
                users = accountService.findAllUser(paramBean);
            }else {
                users= accountService.searchAllUser(paramBean);
            }
            result.setData(users);
                return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            result.setData("查询所有用户异常");
            log.error("查询所有用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 删除用户
     * @param userid
     * @return
     */
    @DeleteMapping(value = "/deleteUser/{userId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult deleteUser(@PathVariable(name = "userId") String userid){
        QueryResult<Object> result = new QueryResult<>();
        try {
           if(accountService.deleteUserByUserId(userid)){
               result.setData("删除成功");
               return new QueryResponseResult(CommonCode.SUCCESS,result);
           }else {
               result.setData("不能删自己");
               return new QueryResponseResult(CommonCode.FAIL,result);
           }


        } catch (Exception e) {
            e.printStackTrace();
            result.setData("删除异常");
            log.error("删除用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 查询所有角色和功能
     * @return
     */
    @GetMapping(value = "/findAllRoleAndFunction")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findAllRoleAndFunction(){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(accountService.findAllRoleAndFunction());
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            result.setData("查询所有角色功能异常");
            log.error("查询所有角色功能异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 通过Uid查询用户
     * @param uid
     * @return
     */
    @GetMapping(value = "/findUserByUid/{uid}")
    @PreAuthorize("hasPermission(null ,null )")
    public QueryResponseResult findUserByUid(@PathVariable(name = "uid") String uid){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(accountService.findUserByUid(uid));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            result.setData("通过id查询用户异常");
            log.error("通过id查询用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 编辑用户
     * @param userr
     * @return
     */
    @PostMapping (value = "/updateUser")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult updateUser(@RequestBody newUser userr){
        QueryResult<Object> result = new QueryResult<>();
        try {
            User user = new User();
            user.setPassword(userr.getPassword());
            user.setPhoneNum(userr.getPhoneNum());
            user.setUsername(userr.getUsername());
            user.setTrueName(userr.getTrueName());
            user.setIsInsider(userr.getIsInsider());

            List<String> roleNameArr = userr.getRoleNameArr();
            List<Role> roles = new ArrayList<>();
            if(roleNameArr!=null&&roleNameArr.size()>0){
                for (String s : roleNameArr) {
                    roles.add(roleMapper.findRoleByRoleName(s));
                }
            }
            user.setRoles(roles);


            List<String> funIds = userr.getFunctionArr();
            List<Function> funs=new ArrayList<>();
            if(funIds!=null&&funIds.size()>0){
                for (String funId : funIds) {
                    funs.add(roleMapper.findFunctionByFid(funId));
                }
            }
            user.setFunctions(funs);
            if(userr.getIsInsider()==0){
                user.setId(accountService.findUserByUsername(user.getUsername()).getId());
                if(userr.getFunctionArr()!=null&&userr.getFunctionArr().size()>0){
                    if(userr.getFunctionArr().contains("AACE73A52DDB4EE79A85112132247167")||
                            userr.getFunctionArr().contains("F7C12D285F7A461F89834FC015DEAB2E")||
                            userr.getFunctionArr().contains("99760B86873B4715BC44EBA5D5553A15")||
                            userr.getFunctionArr().contains("0AB2A73D5235414AA2B04AD002459F38")
                    ){
                        user.setCompanyId(shopId);
                    }
                }
                accountService.updateUser(user);
            }else {
                String data = accountService.updateNotInsiderUser(userr);
                if(!data.equals("编辑成功")){
                    result.setData(data);
                    return new QueryResponseResult(CommonCode.FAIL,result);
                }
            }
            result.setData("编辑成功");
            return new QueryResponseResult(CommonCode.SUCCESS,result);

        } catch (Exception e) {
            e.printStackTrace();
            result.setData("编辑用户异常");
            log.error("编辑用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 登出
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/logout")
    public void logout(HttpServletResponse response) throws IOException {
        response.sendRedirect("/server/logout");
    }

    /**
     * 重置密码
     * @param userid
     * @return
     */
    @GetMapping(value = "/resetPassword/{userid}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult resetPassword(@PathVariable(name = "userid") String userid){
        QueryResult<Object> result = new QueryResult<>();
        try {
            accountService.resetPassword(userid);
            result.setData("密码重置成功");
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            result.setData("密码重置异常");
            log.error("密码重置异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return  new QueryResponseResult(CommonCode.FAIL,result);
        }
    }



    /**
     * 查询用户拥有的功能
     * @return
     */
    @GetMapping(value = "/findNowUser")
    public QueryResponseResult findNowUser(){
        QueryResult<Object> result = new QueryResult<>();
        //获取当前登陆的用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        //通过用户名获取该用户拥有的url
        try {

            result.setData(accountService.findNowUser(username));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取用户拥有的权限异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("获取用户拥有的权限异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }

    }

    /**
     * 查询当前登陆用户的基本信息
     * @return
     */
    @GetMapping(value = "/findNowUserData")
    public QueryResponseResult findNowUserData()  {
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(accountService.findUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        } catch (Exception e) {
            log.error("查询当前登陆用户的基本信息异常");
            log.error(e);
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }

    }

    /**
     * 修改密码
     * @param userId  用户id
     * @param op   新旧密码组合类
     * @return
     */

    @PutMapping(value = "/updatePassword/{userId}")
    public QueryResponseResult updatePassword(@PathVariable(name = "userId") String userId, @RequestBody OldAndNewPassword op){
        try {
            if(accountService.updatePassword(userId,op.getOldPassword(),op.getNewPassword())){
                return new QueryResponseResult(CommonCode.SUCCESS);
            }else {
                return new QueryResponseResult(CommonCode.FAIL);
            }
        }catch (Exception e){
            log.error("修改密码异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.SERVER_ERROR);
        }
    }


}
