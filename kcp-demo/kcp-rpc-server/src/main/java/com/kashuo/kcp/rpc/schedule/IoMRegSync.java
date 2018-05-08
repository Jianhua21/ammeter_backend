package com.kashuo.kcp.rpc.schedule;

import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.result.AmmeterDeviceResult;
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
    private AmmeterService ammeterService;

    @Autowired
    private AuthExceptionService authExceptionService;

    public void regInfo2IoM(){

        //获取注册失败的设备
        List<AmmeterPosition> positions_2 = ammeterPositionService.selectPositionByStatus(2);
        positions_2.forEach(p->{
            try {
                Integer result = commandService.autoRegDevice(p);
                AmmeterPosition position = new AmmeterPosition();
                position.setStatus(result);
                position.setId(p.getId());
                ammeterPositionService.updateByPrimaryKeySelective(position);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,null);
            }
        });
        //获取信息同步失败的设备
        List<AmmeterPosition> positions_4 = ammeterPositionService.selectPositionByStatus(4);
        positions_4.forEach(p->{
            try {
                commandService.autoSyncDeviceInfo(p);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,p.getDeviceId());
            }
        });

    }

    public void sendAddressCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.checkAmmeterMeterNo();
        results.forEach(r->{
            try {
                commandService.getAmmeterAddress(r.getDeviceId());
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("下发电表地址出错,deviceId: {}",r.getDeviceId());
            }catch (Exception e) {
                logger.error("下发电表地址出错,deviceId: {}",r.getDeviceId());
            }
        });
    }

    public void send645PowerDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645PowerDeviceCommand(r);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("下发用电量出错,deviceId: {}",r.getDeviceId());
             } catch (Exception e) {
                logger.error("发送645 电量命令出错!, {}",r.getDeviceId());
            }
        });
    }
    public void send645VoltageDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645VoltageDeviceCommand(r);
            }catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("发送645 电压命令出错!,deviceId: {}",r.getDeviceId());
            } catch (Exception e) {
                logger.error("发送645 电压命令出错!, {}",r.getDeviceId());
            }
        });
    }
    public void send645CurrentDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645CurrentDeviceCommand(r);
            }catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("发送645 电流命令出错!,deviceId: {}",r.getDeviceId());
            }  catch (Exception e) {
                logger.error("发送645 电流命令出错!, {}",r.getDeviceId());
            }
        });
    }

}
