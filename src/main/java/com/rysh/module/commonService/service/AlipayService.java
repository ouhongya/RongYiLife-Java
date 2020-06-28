package com.rysh.module.commonService.service;

import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.KoubeiTradeItemorderQueryRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.alipay.api.response.KoubeiTradeItemorderQueryResponse;
import com.rysh.module.commonService.beans.AlipayRequest;


public interface AlipayService {
    AlipayTradeWapPayResponse pay(AlipayRequest request);
    String pay2(AlipayTradePagePayRequest request);
    KoubeiTradeItemorderQueryResponse pay3(KoubeiTradeItemorderQueryRequest request);
}
