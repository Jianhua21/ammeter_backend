package com.kashuo.kcp.rpc.schedule;

import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.domain.AmmeterPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/28.
 */

@Component
public class IoMRegSync {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmmeterPositionService ammeterPositionService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private AuthExceptionService authExceptionService;

    public void regInfo2IoM(){

        //获取注册失败的设备
        List<AmmeterPosition> positions_2 = ammeterPositionService.selectPositionByStatus(2);
        positions_2.forEach(p->{
            try {
                commandService.autoRegDevice(p);
                AmmeterPosition position = new AmmeterPosition();
                position.setStatus(1);
                position.setId(p.getId());
                ammeterPositionService.updateByPrimaryKeySelective(position);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e);
            }
        });
        //获取信息同步失败的设备
        List<AmmeterPosition> positions_4 = ammeterPositionService.selectPositionByStatus(4);
        positions_4.forEach(p->{
            try {
                commandService.autoSyncDeviceInfo(p);
                AmmeterPosition position = new AmmeterPosition();
                position.setStatus(1);
                position.setId(p.getId());
                ammeterPositionService.updateByPrimaryKeySelective(position);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e);
            }
        });

    }
}
