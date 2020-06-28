package com.rysh.system;

import lombok.Data;

public enum MessageResoult {
    SUCCESS(0,"操作成功"),
    FULL(1,"操作失败"),
    ERROR_SQL_INSET(500,"数据库插入错误"),
    EXCEPTION_DATA_SELECT(800,"数据查询异常")
    ;

    private Message message;

    MessageResoult(int status,String msg){
        message = new Message();
        message.setMsg(msg);
        message.setStatus(status);
    }

    public Message getMessage(Object datas){
        message.setDatas(datas);
        return message;
    }

    public Message getMessage(){
        return message;
    }

    @Data
    public static class Message{
        int status;
        String msg;
        Object datas;
    }

}
