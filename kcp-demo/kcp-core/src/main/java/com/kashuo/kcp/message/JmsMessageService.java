package com.kashuo.kcp.message;

import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.ReceiveMessage;

/**
 * Created by dell-pc on 2019/3/22.
 */
public interface JmsMessageService {

    void sendWechatMessage(ReceiveMessage message);

    void sendThirdPartyNotificationMessage(AmmeterPosition position,String status,String data,String pushType);
}
