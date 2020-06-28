package com.rysh.module.clientMessage.service;

import com.rysh.module.clientMessage.beans.SuperMessage;

public interface MessageService {
    SuperMessage findMessage(String uid, Integer state,Integer time);
}
