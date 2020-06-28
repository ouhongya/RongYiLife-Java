package com.rysh.module.webSocket;


import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{marketId}")
public class WebSocket {

   private static ConcurrentHashMap<String,Session> sessionMap=new ConcurrentHashMap<>();

    /**
     * 连接开启
     * @param marketId
     */
    @OnOpen
    public void init(Session session, @PathParam(value = "marketId") String marketId){
        //有新的连接加入websocket
        sessionMap.put(marketId,session);
        System.out.println("有新的连接,当前在线人数:"+sessionMap.size()+"人。");
    }

    /**
     *接收客户端发送的消息
     */
    @OnMessage
    public void getMessage(String jsonStr){
        //收到消息
        System.out.println(jsonStr);
    }


    /**
     * 连接关闭
     * @param marketId
     */
    @OnClose
    public void close(@PathParam(value = "marketId") String marketId){
        //一个连接被关闭
        sessionMap.remove(marketId);
        System.out.println("有一人退出了蓉意生活，当前在线人数："+sessionMap.size()+"人。");
    }


    /**
     * 推送消息
     * @param message
     * @param marketId
     */
    public void sendMsg(String marketId,String message){
        Session session = sessionMap.get(marketId);
        if(session!=null){
            session.getAsyncRemote().sendText(message);
        }
    }
}
