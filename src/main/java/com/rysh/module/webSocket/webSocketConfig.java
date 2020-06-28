package com.rysh.module.webSocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class webSocketConfig {

//websocket配置类  ↓ 这个bean是自动启动配置了@ServerEndpoint注解的 websocket实现类
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
