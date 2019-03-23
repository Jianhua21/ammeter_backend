package com.kashuo.kcp.message.impl;

import com.alibaba.fastjson.JSON;
import com.kashuo.kcp.dao.AmmeterChannelMapper;
import com.kashuo.kcp.domain.AmmeterChannel;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.PushMessage;
import com.kashuo.kcp.domain.ReceiveMessage;
import com.kashuo.kcp.message.JmsMessageService;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;


/**
 * Created by dell-pc on 2019/3/22.
 */
@Service
public class JmsMessageServiceImpl implements JmsMessageService {

    private Logger logger = LoggerFactory.getLogger(JmsMessageServiceImpl.class);

    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;
    @Value("${queue.name}")
    private String weChatQueue;

    @Value("${thirdparty.notification.queue.name}")
    private String thirdPartyQueue;

    @Autowired
    private AmmeterChannelMapper channelMapper;

    @Override
    public void sendWechatMessage(ReceiveMessage message) {
        try {
            logger.info("发送消息队列的消息内容为:{}",JSON.toJSONString(message));
            Destination destination = new ActiveMQQueue(weChatQueue);
            jmsTemplate.convertAndSend(destination, JSON.toJSONString(message));
        }catch (Exception e){
            logger.error("发送消息到消息队列出错了{}",e);
        }
    }

    @Override
    public void sendThirdPartyNotificationMessage(AmmeterPosition position,String status,String data,String pushType) {
        AmmeterChannel channel = channelMapper.selectByPrimaryKey(position.getChannelId());
        if(channel != null && StringUtils.isNoneEmpty(channel.getThirdParty())) {
            PushMessage message = new PushMessage();
            try {
                message.getPushMessage(position,channel,message,status,data,pushType);
                logger.info("sendThirdPartyNotificationMessage 发送Notification消息队列的消息内容为:{}", JSON.toJSONString(message));
                Destination destination = new ActiveMQQueue(thirdPartyQueue);
                jmsTemplate.convertAndSend(destination, JSON.toJSONString(message));
            } catch (Exception e) {
                logger.error("sendThirdPartyNotificationMessage 发送消息到消息队列出错了{}", e);
            }
        }
    }
}
