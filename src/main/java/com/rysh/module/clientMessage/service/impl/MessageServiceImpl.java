package com.rysh.module.clientMessage.service.impl;

import com.rysh.module.clientMessage.beans.Message;
import com.rysh.module.clientMessage.beans.SuperMessage;
import com.rysh.module.clientMessage.mapper.MessageMapper;
import com.rysh.module.clientMessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    /**
     * 查询消息
     * @param uid
     * @param state
     * @return
     */
    @Override
    public SuperMessage findMessage(String uid, Integer state,Integer time) {
        //通过uid查询用户消息
        List<Message> messages=messageMapper.findMessage(uid,state,time*30);
        SuperMessage superMessage = new SuperMessage();
        superMessage.setMesCount(messages.size());
        superMessage.setMessages(messages);
        if(state!=1){
            //当前查询的是未读消息，修改消息状态
            for (Message message : messages) {
                messageMapper.updateMessageIsRead(message.getId());
                //设置时间毫秒值
                message.setCreateTimeMills(message.getCreatedTime().getTime());
            }
        }
        return superMessage;
    }
}
