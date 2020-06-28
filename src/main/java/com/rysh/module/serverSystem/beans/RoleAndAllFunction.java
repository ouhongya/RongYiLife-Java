package com.rysh.module.serverSystem.beans;

import com.rysh.module.farm.beans.FarmAndUser;
import com.rysh.module.grange.beans.Grange;
import lombok.Data;

import java.util.List;
@Data
public class RoleAndAllFunction {
    private List<Role> roles;   //角色集合
    private List<ParentFandSonF> ps;   //一级二级菜单集合
    private Role role;      //
    private List<Store> stores;  //商铺集合
    private List<FarmAndUser> farmAndUsers;    //农场集合
    private List<Grange> granges;   //农庄集合




}
