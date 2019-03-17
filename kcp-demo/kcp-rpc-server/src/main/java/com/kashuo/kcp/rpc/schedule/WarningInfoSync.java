package com.kashuo.kcp.rpc.schedule;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.api.entity.callback.DeviceZxYunGateWay;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.NbiotConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterWarningService;
import com.kashuo.kcp.domain.AmmeterAuth;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.entity.ZxYunMessage;
import com.kashuo.kcp.utils.ZxYunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.kashuo.kcp.utils.ZxYunUtils.getGateway;

/**
 * Created by dell-pc on 2018/4/9.
 */
@Component
public class WarningInfoSync {
    @Autowired
    private AmmeterWarningService warningService;
    @Autowired
    private AmmeterPositionService positionService;
    @Autowired
    private CommandService commandService;
    Logger logger = LoggerFactory.getLogger(getClass());

    public void updateWarningInfoByCron() throws Exception {
        logger.info("定时把已经长时间失去连接和信号量不正常的列出警告");
        warningService.genereateWellCoverWarning();
    }

    public void updateOfflineDevice() throws Exception{
        logger.info("=================更新3小时未收到数据设备未上电================");
        warningService.updateOfflineDeviceStatus();
    }

    public void updateZxYunStates()throws Exception{
        logger.info("=====================更新中消云设备在线状态====================");
        List<AmmeterPosition> positions = positionService.queryPositionByPlatform(3);
        positions.forEach(p->
              commandService.updateZxYunStates(p)
        );
    }
}
