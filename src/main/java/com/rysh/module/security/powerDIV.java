package com.rysh.module.security;


import com.rysh.module.serverSystem.service.AccountService;
import com.rysh.module.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

@Component
public class powerDIV implements PermissionEvaluator {



    /**
     * security中的鉴定权限方法，决定拦截或者不拦截
     *
     * @param authentication 包含登陆时的user对象
     * @param obj            通过方法传递   null
     * @param o1             通过方法传递   null
     * @return true=放行   false=权限不足
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object obj, Object o1) {

        //获取登陆之后的user对象
        User account = (User) authentication.getPrincipal();
        //获取登陆时注入的权限
        Collection<GrantedAuthority> powers = account.getAuthorities();

        return checkPower(powers);



    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return true;
    }

    /**
     * 判断用户是否具有该方法的权限
     *
     * @param powers 权限集合
     * @return
     */
    public Boolean checkPower(Collection<GrantedAuthority> powers) {
        //创建获取request的工具类
        HttpUtils httpUtils = new HttpUtils();
        //获取request
        HttpServletRequest request = httpUtils.getRequest();
        //获取url
        String requestURI = request.getRequestURI();
        requestURI=interceptString(requestURI);
        //遍历权限集合，并和当前url对比
        for (GrantedAuthority power : powers) {
            String powerStr = power.getAuthority();
            if (requestURI.equals(powerStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 切割url字符串
     * @param url
     * @return
     */
    public String interceptString(String url) {

        return url.split("/")[2];
    }
}
