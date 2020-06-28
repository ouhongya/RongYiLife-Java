package com.rysh.module.serverSystem.controller;
import com.rysh.module.commonService.beans.ParamBean;
import com.rysh.module.serverSystem.beans.Role;
import com.rysh.module.serverSystem.beans.RoleAndFunIds;
import com.rysh.module.serverSystem.service.RoleService;
import com.rysh.system.response.CommonCode;
import com.rysh.system.response.QueryResponseResult;
import com.rysh.system.response.QueryResult;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@RestController
@RequestMapping(value = "/server/character")
@Api(description = "角色设置接口")
public class ServerRoleController {

    private final SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/addRole")
    @PreAuthorize("hasPermission(null ,null)")
    public QueryResponseResult addRole(@RequestBody RoleAndFunIds role){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(roleService.addRole(role)){
                result.setData("添加成功");
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                result.setData("添加失败，该角色名已存在!");
                return new QueryResponseResult(CommonCode.FAIL,result);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setData("添加角色异常");
            log.error("添加角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }

    }
    /**
     * 删除角色
     * @param
     * @return
     */
    @DeleteMapping(value = "/deleteRole/{roleId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult deleteRole(@PathVariable(name = "roleId") String roleId){
        QueryResult<Object> result = new QueryResult<>();
        try {
            boolean b = roleService.deleteRole(roleId);
            if(b){
                result.setData("删除成功");
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                result.setData("删除失败,该角色绑定了用户,请到管理中心解绑");
                return new QueryResponseResult(CommonCode.FAIL,result);
            }


        } catch (Exception e) {
            e.printStackTrace();
            result.setData("删除异常");
            log.error("删除角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 编辑角色中的功能
     * @param   role 角色对象
     * @param
     * @return
     */
    @PostMapping(value = "/updateRole")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult updateRole(@RequestBody Role role){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(roleService.updateRole(role)){
                result.setData("编辑成功");
                return new QueryResponseResult(CommonCode.SUCCESS,result);
            }else {
                result.setData("该角色名称已存在！");
                return new QueryResponseResult(CommonCode.FAIL,result);
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.error("编辑角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("编辑角色异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    /**
     * 查询所有功能
     * @return
     */
    @GetMapping(value = "/findAllFunction")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findAllFunction(){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(roleService.findAllFunction());
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询所有功能异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("查询所有功能异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    @GetMapping(value = "/findAllRole")
    @PreAuthorize("hasPermission(null ,null)")
    public QueryResponseResult findAllRole(ParamBean paramBean){
        QueryResult<Object> result = new QueryResult<>();
        try {
            if(paramBean.getSearch()==null||"".equals(paramBean.getSearch())){
                result.setData(roleService.findAllRole(paramBean));
            }else {
                result.setData(roleService.searchAllRole(paramBean));
            }
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("查询所有角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("查询所有角色异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }

    @GetMapping(value = "/findRoleByRid/{roleId}")
    @PreAuthorize("hasPermission(null,null)")
    public QueryResponseResult findRoleByRid(@PathVariable(name = "roleId") String roldId){
        QueryResult<Object> result = new QueryResult<>();
        try {
            result.setData(roleService.findRoleByRid(roldId));
            return new QueryResponseResult(CommonCode.SUCCESS,result);
        }catch (Exception e){
            e.printStackTrace();
            log.error("通过Rid查询角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            result.setData("通过Rid查询角色异常");
            return new QueryResponseResult(CommonCode.FAIL,result);
        }
    }



}
