package com.rysh.module.redis;

import com.rysh.module.clientMessage.mapper.MessageMapper;
import com.rysh.module.clientOrders.beans.Orders;
import com.rysh.module.clientOrders.beans.OrdersSplit;
import com.rysh.module.clientOrders.beans.StateAndOrdersId;
import com.rysh.module.clientOrders.mapper.OrdersMapper;
import com.rysh.module.mobileUser.beans.UserIdAndScore;
import com.rysh.module.mobileUser.mapper.UserMapper;
import com.rysh.module.utils.GenerateUUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class redisListener extends KeyExpirationEventMessageListener {
    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MessageMapper messageMapper;

    public redisListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * redis消息监听方法
     * @param message   message.body = 过期的key     message.Channel = 当前过期的值是属于哪个数据库
     * @param pattern   监听策略   （就是监听的哪个数据库，在redisConfig中可以配置特定监听某个数据库   不配置就监听所有）
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //获取过期的key
            String mes=new String(message.getBody());
            //转换成字符数组
            char[] chars = mes.toCharArray();
            //与手机端登陆区分
        if(chars.length==32){
            //当数组长度等于32时  说明是订单超时  调用ordersMapper修改总订单状态
            StateAndOrdersId so = new StateAndOrdersId();
            so.setOrdersId(mes);
            so.setState(4);
            ordersMapper.updateOrdersState(so,null,null);
            //修改子订单状态
            so.setState(0);
            ordersMapper.updateOrdersSplitStatus(so);
            //根据总订单id查询用户id
            Orders orders=ordersMapper.findOrdersByOrderId(mes);
            //查询此订单所用积分
            Integer score=0;
            List<OrdersSplit> splitOrderss = ordersMapper.findAllSplitByOrdersId(orders.getId());
            for (OrdersSplit splitOrders : splitOrderss) {
                score+=splitOrders.getUsedSorce();
            }
            //构建用户id和使用积分组合类
            UserIdAndScore us = new UserIdAndScore();
            us.setUserId(orders.getUserId());
            us.setScore(0-score);
            //归还用户积分
            userMapper.updateUserScore(us);
            //创建消息对象
            com.rysh.module.clientMessage.beans.Message mess = new com.rysh.module.clientMessage.beans.Message();
            //设置消息属性
            //主键id
            mess.setId(GenerateUUID.create());
            //消息体
            mess.setContent("订单号为："+orders.getOrdersNum()+" 的订单超时未支付，已取消。");
            //消息对应的用户id
            mess.setUserId(orders.getUserId());
            //消息创建时间
            mess.setCreatedTime(new Date());
            //存入消息表
            messageMapper.sendMessage(mess);
        }else if(chars.length==33) {
            String s = String.valueOf(chars[chars.length-1]);
            if("2".equals(s)){
                //自动确认收货
                String splitId = mes.substring(0, 32);
                ordersMapper.updateOrdersSplitState(splitId,3);
            }
        }
    }
}
