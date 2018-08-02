package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.*;
import com.kashuo.kcp.dao.condition.CallBackCondition;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.AmmeterConfig;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.redis.RedisService;
import com.kashuo.kcp.redis.RedisServiceImpl;
import com.kashuo.kcp.utils.AmmeterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/23.
 */
@Service
public class AmmeterCallBackService {

    @Autowired
    private AmmeterCallbackHistoryMapper callbackHistoryMapper;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterDeviceMapper ammeterDeviceMapper;

    @Autowired
    private AmmeterReportServer ammeterReportServer;

    @Autowired
    private AmmeterWorkingInfoMapper workingInfoMapper;

    @Autowired
    private AmmeterPositionMapper positionMapper;

    @Autowired
    private AmmeterConfigMapper configMapper;

    @Autowired
    private RedisServiceImpl redisService;

    public Page<AmmeterCallbackHistory> getCallBackHistoryByDeviceId(CallBackCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return callbackHistoryMapper.getCallBackHistoryByDeviceId(condition.getDeviceId());
    }

    public void insertCallBackHistory(AmmeterCallbackHistory callbackHistory){
        if(callbackHistory.getDeviceId() == null){
            callbackHistory.setDeviceId("-");
        }
        if(callbackHistory.getNotifyType() == null){
            callbackHistory.setNotifyType("-");
        }
        callbackHistoryMapper.insert(callbackHistory);
    }

    public void process645dltData(CommandDetail detail,String deviceId) throws Exception {
        System.out.println("==============="+sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_AMMETER_ADDRESS_KEY,
                AppConstant.CALLBACK_URLS_TYPE_ID));
        System.out.println("==============="+detail.getCommand());
        if(detail.getCommand().equals(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_AMMETER_ADDRESS_KEY,
                AppConstant.CALLBACK_URLS_TYPE_ID))){
            String address = String.valueOf(AmmeterUtils.unPackageAnalysisForAddress(detail.getData()).get("address"));
            if(address != null && address.length() ==12) {
                ammeterDeviceMapper.updateMeterNoByDeviceId(deviceId, address);
                redisService.set(deviceId,"0");
            }else{
                String  limits  = redisService.get(deviceId);
                if(limits != null){
                    try {
                        redisService.set(deviceId,String.valueOf(Integer.parseInt(limits)+1));
                    }catch (Exception e){
                        redisService.set(deviceId,"0");
                    }
                }
            }
        }else {
            AmmeterDevice device = ammeterDeviceMapper.selectByDeviceId(deviceId);
            String power = AmmeterUtils.getPackageCommand(device.getMeterNo(),sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_AMMETER_POWER_KEY,
                    AppConstant.CALLBACK_URLS_TYPE_ID));
            String voltage = AmmeterUtils.getPackageCommand(device.getMeterNo(),sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_AMMETER_VOLTAGE_KEY,
                    AppConstant.CALLBACK_URLS_TYPE_ID));
            String current = AmmeterUtils.getPackageCommand(device.getMeterNo(),sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_AMMETER_CURRENT_KEY,
                    AppConstant.CALLBACK_URLS_TYPE_ID));
            if(detail.getCommand().equals(power)){
                //记录电量值
                if(!detail.getData().contains("ERR")) {
                    ammeterReportServer.processDailyReportServer(device, detail.getData(), 1);
                }

            }else if(detail.getCommand().equals(voltage)){
                //记录电压值
                if(!detail.getData().contains("ERR")) {
                    ammeterReportServer.processDailyReportServer(device, detail.getData(), 2);
                }
            }else if(detail.getCommand().equals(current)){
                //记录电流值
                if(!detail.getData().contains("ERR")) {
                    ammeterReportServer.processDailyReportServer(device, detail.getData(), 3);
                }
            }

        }

    }

    /***
     * 处理开关闸返回信息
     */
    public void processSwitchPower(CommandDetail detail,String deviceId,boolean powerFlag){
        AmmeterDevice device = ammeterDeviceMapper.selectByDeviceId(deviceId);
        if(device != null){
            if(powerFlag){
                //合闸成功
                if(!detail.getData().contains("ERR")) {
                    workingInfoMapper.updateByAmmeterId(device.getId(), 2);
                }else{
                    //合闸失败,依然是拉闸状态
                    workingInfoMapper.updateByAmmeterId(device.getId(), 1);
                }
            }else{
                //拉闸成功
                if(!detail.getData().contains("ERR")) {
                    workingInfoMapper.updateByAmmeterId(device.getId(), 1);
                }else{
                    //拉闸失败,依然是合闸状态
                    workingInfoMapper.updateByAmmeterId(device.getId(), 2);
                }
            }
        }
    }

    /***
     * 系统运行配置返回处理
     */
    public void processRunningConfig(CommandDetail detail,String deviceId){

        AmmeterPosition position = positionMapper.selectByDeviceId(deviceId);

        if(position == null){
            return;
        }
        AmmeterConfig config = new AmmeterConfig();
        config.setPositionId(position.getId());
        //CDP IP
        if(detail.getCommand().equals(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_CDP_IP_KEY,
                AppConstant.CALLBACK_URLS_TYPE_ID))){
            if(!detail.getData().contains("ERR")){
                config.setCdpStatus(0);
            }else{
                config.setCdpStatus(1);
            }
        }else if(detail.getCommand().equals(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_APN_ADDRESS_KEY,
                AppConstant.CALLBACK_URLS_TYPE_ID))){
            //设置APN
            if(!detail.getData().contains("ERR")){
                config.setApnStatus(0);
            }else{
                config.setApnStatus(1);
            }
        }else if(detail.getCommand().equals(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_NB_KEEPALIVE_KEY,
                AppConstant.CALLBACK_URLS_TYPE_ID))){
            //设置NB保活时间
            if(!detail.getData().contains("ERR")){
                config.setNbStatus(0);
            }else{
                config.setNbStatus(1);
            }
        }
        configMapper.updateStatusByPositionKeySelective(config);
    }
}
