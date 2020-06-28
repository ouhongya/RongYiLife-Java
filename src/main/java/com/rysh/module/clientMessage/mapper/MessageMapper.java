package com.rysh.module.clientMessage.mapper;

import com.rysh.module.clientMessage.beans.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    List<Message> findMessage(@Param("uid") String uid,@Param("state") Integer state,@Param("time") Integer time);

    void sendMessage(@Param("message") Message message);

    void updateMessageIsRead(String id);
}
