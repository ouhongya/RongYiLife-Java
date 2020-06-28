package com.rysh.module.Filter;


import com.rysh.module.serverSystem.service.impl.UserDetialsServiceImpl;
import com.rysh.module.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CheckTokenFilter extends BasicAuthenticationFilter {
    @Autowired
   private UserDetialsServiceImpl userDetials;

    public CheckTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token =null;
        //从cookie中获取token
        Cookie[] cookies = request.getCookies();
        if(cookies==null||cookies.length<=0){

        }else {
            for (Cookie cookie : cookies) {
                if("Token".equals(cookie.getName())){
                    token=cookie.getValue().trim();
                }
            }
        }
        //获取请求头中的Token

       if(token==null){
           chain.doFilter(request,response);
       }else {
           //校验token
           Map<String, Object> tokenMap = TokenUtils.checkToken(token);
           //获取验证结果
           String tokenStatus = (String) tokenMap.get("state");
           //判断是否验证成功
           if("校验成功".equals(tokenStatus)){
               //给用户重新生成token
               //获取用户名
               Map map = (Map) tokenMap.get("data");
               String username = (String) map.get("uid");
               Map tmap=new HashMap();
               tmap.put("uid",username);
               tmap.put("isInsider",map.get("isInsider"));
               tmap.put("iat",new Date().getTime());
               tmap.put("ext",new Date().getTime()+10800000);
               if(map.get("itemId")!=null){
                   tmap.put("itemId",map.get("itemId"));
               }
               String token1 = TokenUtils.createToken(tmap);
               response.setHeader("token",token1);
               //为本次会话设置权限
               UserDetails user = userDetials.loadUserByUsername(username);
               //创建token通过认证
               UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
               authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               // 将权限写入本次会话
               SecurityContextHolder.getContext().setAuthentication(authentication);
               //放行
               chain.doFilter(request,response);
           }else{
               //token过期了  或者token验证不正确  转发到登陆页面
               response.sendRedirect("/logout");
           }
       }
    }
}
