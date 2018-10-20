package com.kashuo.kcp.manage;

import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.redis.RedisService;
import com.kashuo.kcp.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/10/20.
 */
@Service
public class MessageService {

    @Autowired
    private RedisService redisService;

    public void sendMessageContent(AmmeterPosition position, String status){
        String value = redisService.get(""+position.getImei()+"-message-"+status);
        if(value == null || value.length()==0) {
            MessageUtils.sendMessage(position.getImei(), status, position.getContactInfo());
            redisService.set(position.getImei()+"-message-"+status,"1");
            redisService.expire(position.getImei() + "-message-" + status, 60 * 60 * 24);
        }
    }
}
