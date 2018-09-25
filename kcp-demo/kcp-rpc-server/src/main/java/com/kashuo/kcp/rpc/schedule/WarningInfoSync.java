package com.kashuo.kcp.rpc.schedule;

import com.kashuo.kcp.core.AmmeterWarningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dell-pc on 2018/4/9.
 */
@Component
public class WarningInfoSync {
    @Autowired
    private AmmeterWarningService warningService;

    Logger logger = LoggerFactory.getLogger(getClass());

    public void updateWarningInfoByCron() throws Exception {
        logger.info("定时把已经长时间失去连接和信号量不正常的列出警告");
        warningService.genereateWellCoverWarning();
    }

    public void updateOfflineDevice() throws Exception{
        logger.info("更新3小时未收到数据设备未上电");
        warningService.updateOfflineDeviceStatus();
    }
}
