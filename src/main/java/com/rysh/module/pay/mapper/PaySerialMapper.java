package com.rysh.module.pay.mapper;

import com.rysh.module.pay.beans.PayOrderError;
import com.rysh.module.pay.beans.PaySerial;

public interface PaySerialMapper {
    void addPaySerial(PaySerial paySerial);
    void addPayOrderError(PayOrderError payOrderError);
}
