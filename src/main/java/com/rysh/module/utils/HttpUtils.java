package com.rysh.module.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取request的工具类
 */
public class HttpUtils {
  private   ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    //获取request
  public HttpServletRequest getRequest(){
      HttpServletRequest request = servletRequestAttributes.getRequest();
      return request;
  }
    //获取response
  public HttpServletResponse getResponse(){
      HttpServletResponse response = servletRequestAttributes.getResponse();
      return response;
  }
}
