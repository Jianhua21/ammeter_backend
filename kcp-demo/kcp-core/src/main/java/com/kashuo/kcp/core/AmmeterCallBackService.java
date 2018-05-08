package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.api.entity.CommandDetail;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.AmmeterCallbackHistoryMapper;
import com.kashuo.kcp.dao.AmmeterDeviceMapper;
import com.kashuo.kcp.dao.condition.CallBackCondition;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import com.kashuo.kcp.domain.AmmeterDevice;
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
            String address = AmmeterUtils.unPackageAnalysis(detail.getData());
            ammeterDeviceMapper.updateMeterNoByDeviceId(deviceId,address);
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
}
