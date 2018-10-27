package com.kashuo.kcp.core;

import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.*;
import com.kashuo.kcp.dao.condition.AmmeterHandleResult;
import com.kashuo.kcp.dao.condition.AmmeterNetWorkCondition;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by dell-pc on 2017/9/22.
 */
@Service
public class NetWorkService {

    private final static Logger logger = LoggerFactory.getLogger(NetWorkService.class);

    @Autowired
    private AmmeterNetworkMapper networkMapper;
    @Autowired
    private SysDictionaryService dictionaryService;

    @Autowired
    private AmmeterDeviceMapper ammeterDeviceMapper;
    @Autowired
    private AmmeterRuleService ammeterRuleService;

    @Autowired
    private AmmeterPositionMapper ammeterPositionMapper;

    @Autowired
    private AmmeterWarningMapper ammeterWarningMapper;
    @Autowired
    private CommandService commandService;

    public String getNetWorkResponseByAmmeterId(Integer ammeterId,String ammeterNumber){
        String responseStr ="-1";
        AmmeterNetwork network = networkMapper.selectByAmmeterId(ammeterId);
        if("".equals(dictionaryService.getDynamicValue(32))){
            return responseStr;
        }
        if(network != null){

            if(new Date().getTime() - network.getCreatedTime().getTime() > 30*60*1000l )  {
                responseStr =  dictionaryService.getDynamicValue(32,ammeterNumber);
            }

        }else {
            responseStr =  dictionaryService.getDynamicValue(32,ammeterNumber);
        }
        return responseStr;
    }

    public String getNetWorkResponseByAmmeterId(Integer ammeterId){
        String responseStr ="-1";
        AmmeterNetwork network = networkMapper.selectByAmmeterId(ammeterId);
        if("".equals(dictionaryService.getDynamicValue(32))){
            return responseStr;
        }
        if(network != null){
            if(new Date().getTime() - network.getCreatedTime().getTime() > 30*60*1000l )  {
                responseStr =  dictionaryService.getDynamicValue(32);
            }
        }else {
            responseStr =  dictionaryService.getDynamicValue(32);
        }
        return responseStr;
    }


    public AmmeterNetwork selectByAmmeterId(Integer ammeterId){
        return networkMapper.selectByAmmeterId(ammeterId);
    }

    public void insertNetWorkInfo(String message, String deviceId){
        AmmeterDevice ammeterDevice = ammeterDeviceMapper.selectByDeviceId(deviceId);
        AmmeterNetwork networkDB = networkMapper.selectByAmmeterId(ammeterDevice.getId());
        String[] params = message.split(",");
        if(networkDB != null){
            if(DateUtils.getCurrentDate().equals(networkDB.getRecordDay()) && networkDB.getRecordHour() == DateUtils.getHour()){
                setNetworkInfo(networkDB,params,ammeterDevice);
                networkMapper.updateByPrimaryKeySelective(networkDB);
            }else{
                insertNetWork(params,ammeterDevice);
            }
        }else{
            insertNetWork(params,ammeterDevice);
        }
        updateDeviceStatusByNb(deviceId,ammeterDevice,true);
    }

    public void updateDeviceStatusByNb(String deviceId,AmmeterDevice ammeterDevice,boolean flag){
        if(ammeterDevice != null) {

        }else{
            ammeterDevice = ammeterDeviceMapper.selectByDeviceId(deviceId);
        }
        AmmeterPosition positionDB = ammeterPositionMapper.selectByDeviceId(deviceId);
        if(flag && positionDB != null && positionDB.getStatus() != 6) {
            //更新在线状态
            AmmeterPosition position = new AmmeterPosition();
            position.setDeviceId(deviceId);
            position.setStatus(6);
            ammeterPositionMapper.updateStatusByDeviceId(position);
            //设备不在线警告 消除
            AmmeterWarning warning = new AmmeterWarning();
            warning.setWarningType(1);
            warning.setWarningStatus("1");
            warning.setAmmeterId(ammeterDevice.getId());
            ammeterWarningMapper.updateStatusByType(warning);

            //下发电表地址
            try {
                commandService.getAmmeterAddress(position.getDeviceId());
            } catch (NorthApiException e) {
                logger.error("下发电表地址出错,deviceId: {}",position.getDeviceId());
            }catch (Exception e) {
                logger.error("下发电表地址出错,deviceId: {}",position.getDeviceId());
            }
        }
        if(!flag){
            //更新在线状态
            AmmeterPosition position = new AmmeterPosition();
            position.setDeviceId(deviceId);
            position.setStatus(7);
            ammeterPositionMapper.updateStatusByDeviceId(position);
            //设备不在线警告 消除
            AmmeterWarning warning = new AmmeterWarning();
            warning.setWarningType(1);
            warning.setWarningStatus("0");
            warning.setAmmeterId(ammeterDevice.getId());
            ammeterWarningMapper.updateStatusByType(warning);
        }


    }

    public void insertNetWork(String[] params,AmmeterDevice ammeterDevice){
        AmmeterNetwork network = new AmmeterNetwork();
        setNetworkInfo(network,params,ammeterDevice);
        network.setCreatedTime(new Timestamp(new Date().getTime()));
        network.setAmmeterId(ammeterDevice.getId());
        network.setRecordDay(DateUtils.getCurrentDate());
        network.setRecordHour(DateUtils.getHour());
        networkMapper.insert(network);
    }

    public  void setNetworkInfo(AmmeterNetwork network,String[] params,AmmeterDevice ammeterDevice){
        for (String param : params) {
            if(param.startsWith(AppConstant.METER_RSSI_KEY)){
                network.setRssi(param.substring(5));
            }else if(param.startsWith(AppConstant.METER_RSRQ_KEY)){
                network.setRsrq(param.substring(5));
            }else if(param.startsWith(AppConstant.METER_CELLI_KEY)){
                network.setCelli(param.substring(7));
            }else if(param.startsWith(AppConstant.METER_RSRP_KEY)){
                network.setRsrp(param.substring(5));
            }
        }
        network.setCreatedTime(new Date());
        //对RSRQ无线信号 告警判断
        boolean flag = ammeterRuleService.checkNetWorkWarning(network,AppConstant.METER_RSRQ_KEY.toLowerCase());
        if(flag){
            if(ammeterDevice.getRsrqWarningFlag()< 3) {
                ammeterDevice.setRsrqWarningFlag(ammeterDevice.getRsrqWarningFlag() + 1);
                ammeterDeviceMapper.updateWarningStatusByPrimaryKey(ammeterDevice);
            }
        }else {
            if(ammeterDevice.getRsrqWarningFlag()>0) {
                ammeterDevice.setRsrqWarningFlag(0);
                ammeterDeviceMapper.updateWarningStatusByPrimaryKey(ammeterDevice);
                AmmeterWarning warning = new AmmeterWarning();
                warning.setWarningType(0);
                warning.setWarningStatus("1");
                warning.setAmmeterId(ammeterDevice.getId());
                ammeterWarningMapper.updateStatusByType(warning);
            }
        }
    }



    public List<AmmeterNetwork>  queryNetWorkParams(AmmeterNetwork network){
        return networkMapper.queryNetWorkParams(network);
    }
    public Integer getMaxHourByReportDate(List<AmmeterNetwork> netWorkReport){
        Integer maxvalue = 0;
        for (AmmeterNetwork report:netWorkReport) {
            if(maxvalue < report.getRecordHour()){
                maxvalue = report.getRecordHour();
            }
        }
        return maxvalue;
    }

    public Page<AmmeterNetWorkResult> networkList(AmmeterNetWorkCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return networkMapper.networkList(condition);
    }


    public static void main(String[] args) {
        String param ="meter:0x2800001+rssi:-50+rsrq:-70+cellid:124";
        AmmeterNetwork network = new AmmeterNetwork();
        String[] params = param.split("\\+");

        for (String data:params){
            System.out.println(data);
            if(data != null){
                if(data.contains("rssi:")){
                    System.out.println(data.indexOf("rssi"));
                    network.setRssi(data.substring(5));
                }else if(data.contains("rsrq:")){
                    network.setRsrq(data.substring(5));
                }else if(data.contains("cellid:")){
                    network.setCelli(data.substring(7));
                }
            }
        }
    }
}
