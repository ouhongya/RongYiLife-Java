package com.rysh.module.commonService.service;

import com.rysh.module.commonService.beans.WxpayRequest;


public interface WxpayService {
    public String makePreOrder(WxpayRequest request);
}
