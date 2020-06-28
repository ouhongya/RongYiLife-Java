package com.rysh.module.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class redisConfig {

    /**
     * redis监听类配置
     */
    @Configuration
    public class RedisListenerConfig {
        @Bean
        RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory){
            //构建redis监听容器
            RedisMessageListenerContainer container = new RedisMessageListenerContainer();
            //与redis服务器建立连接
            container.setConnectionFactory(redisConnectionFactory);
            //配置需要监听的redis数据库  不配置 就监听所有
//            container.addMessageListener(new redisListener(container), new PatternTopic("__keyevent@3__:expired"));
            return container;
        }
    }

}
