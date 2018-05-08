package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.AmmeterDeviceMapper;
import com.kashuo.kcp.dao.AmmeterWorkingInfoMapper;
import com.kashuo.kcp.dao.condition.AmmeterHandleCondition;
import com.kashuo.kcp.dao.condition.AmmeterHandleResult;
import com.kashuo.kcp.dao.condition.ServicesParams;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterReport;
import com.kashuo.kcp.domain.AmmeterWorkingInfo;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.utils.AmmeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/14.
 */
@Service
public class AmmeterHandleService {


    private Logger logger = LoggerFactory.getLogger(AmmeterHandleService.class);
    @Autowired
    private AmmeterReportServer reportServer;
    @Autowired
    private NetWorkService netWorkService;
    @Autowired
    private AmmeterDeviceMapper ammeterDeviceMapper;
    @Autowired
    private AmmeterWorkingInfoMapper ammeterWorkingInfoMapper;
    @Autowired
    private AmmeterService ammeterService;
    @Autowired
    private SysDictionaryService sysService;


    public AmmeterHandleResult handleIomData(AmmeterHandleCondition condition,boolean isDebug){
        AmmeterHandleResult result =new AmmeterHandleResult();
        if(isDebug) {
            if (condition.getServices().get(0).getData().getParams().startsWith("IMSI:")) {
                AmmeterDevice device = new AmmeterDevice();
                device.setInputMsg(condition.getServices().get(0).getData().getParams());
                device.setAddress("");
                String responseCommand = ammeterService.updateAmmeterDevice(device);
                result.setCommand("FEFEFEFE68111111111111681104373733372316");
                result.setCrcValue("7922");
                result.setData("00");
                result.setProtocolHander("89");
                result.setProtocolVersion("01");
                result.setAmmeterType("03");
                result.setLength("20");
                //89010320007922
            }else{
                updateAmmeterDevice(condition);
            }
        }else {
            updateAmmeterDevice(condition);
//            DeviceManagement
        }

        return  result;
    }


    @Transactional
    public String updateAmmeterDevice(AmmeterHandleCondition condition) {
        String response ="-1";
        List<SysDictionary> list = sysService.getDictionartLists();
        AmmeterDevice device = ammeterDeviceMapper.selectByImsiKey(condition.getDeviceId());
         if(device == null){
            return response;
        }
        if(list != null) {
            for (int i = 1; i <= list.size(); i++) {
                SysDictionary sysDictionary = sysService.getDictionaryByIndex(i);
                ServicesParams servicesParams = condition.getServices().get(0);
                logger.info("dictionary :"+i+": "+sysDictionary.getParamId()+" 当前inputMsg:"+servicesParams.getData());
                AmmeterHandleResult result = processDataParams(servicesParams.getData().getParams());
                if (result.getRealCommnd().equals((sysService.getDynamicValue(sysDictionary.getParamId())))){

                    //截取AmmeterNumber
                    //无线参数判断
                    if(sysDictionary.getParamId()>=40 && sysDictionary.getParamId() <=42){
//                        netWorkService.insertNetWorkInfo(device,result);
                        return ammeterService.getSocketRequestParam(device,
                                ammeterWorkingInfoMapper.selectByAmmeterId(device.getId()),false);
                    }
                    //set 参数
                    if(sysDictionary.getParamId() > 12 && sysDictionary.getParamId() != 29 && sysDictionary.getParamId() != 33) {
                        //更新电表固有信息表
                        device.setParams(result.getData(), sysDictionary.getParamId(), device);
                        ammeterDeviceMapper.updateByImsiKeySelective(device);
                        return getSocketRequestParam(device,ammeterWorkingInfoMapper.selectByAmmeterId(device.getId()),false);
                    }else{
                        //更新电表工作参数
                        AmmeterWorkingInfo ammeterWorkingInfo = new AmmeterWorkingInfo();
                        ammeterWorkingInfo.setParams(result.getCommand(),sysDictionary.getParamId(),ammeterWorkingInfo);
                        ammeterWorkingInfo.setAmmeterId(device.getId());
                        AmmeterWorkingInfo info = ammeterWorkingInfoMapper.selectByAmmeterId(device.getId());
                        if(info != null){
                            info.setParams(result.getData(),sysDictionary.getParamId(),info);
                            ammeterWorkingInfoMapper.updateByAmmeterIdSelective(info);
                        }else{
                            ammeterWorkingInfo.setAmmeterId(device.getId());
                                ammeterWorkingInfo.setParams(result.getData(),sysDictionary.getParamId(),ammeterWorkingInfo);
                            ammeterWorkingInfoMapper.insert(ammeterWorkingInfo);
                            info = ammeterWorkingInfo;
                        }
                        if(sysDictionary.getParamId() == 11 ){
                            reportServer.insertDailyReportServer(device,result.getData());
                        }
                        return getSocketRequestParam(device,info,false);
                    }
                }
            }
            return response;
        }else{
            return response;
        }
    }
    public  String getSocketRequestParam(AmmeterDevice ammeterDevice,
                                         AmmeterWorkingInfo ammeterWorkingInfo,
                                         boolean firstResponse){
        if(firstResponse) {

        }else{
            //无线参数
            String netWorkStr = netWorkService.getNetWorkResponseByAmmeterId(ammeterDevice.getId());
            if(!"-1".equals(netWorkStr)){
                return netWorkStr;
            }
            int infoIndex = ammeterWorkingInfo.returnWorkInfoIndex(ammeterWorkingInfo);
            if(infoIndex == 29 && ammeterWorkingInfo.getStatus() != null && ammeterWorkingInfo.getStatus() ==3){
                return sysService.getDynamicValue(infoIndex);
            }else if(infoIndex == 33 && ammeterWorkingInfo.getStatus() != null &&  ammeterWorkingInfo.getStatus() == 4) {
                return sysService.getDynamicValue(infoIndex);
            }
            AmmeterReport report = new AmmeterReport();
            report.setAmmeterId(ammeterDevice.getId());
            String reportParams = reportServer.sendReponseParamsForReport(report);
            if(!"-1".equals(reportParams)){
                return reportParams;
            }
            int deviceIndex = ammeterDevice.returnParamIndex(ammeterDevice);
            if(deviceIndex != -1){

                return sysService.getDynamicValue(deviceIndex);
            }else{
                if(infoIndex != -1)
                    return sysService.getDynamicValue(infoIndex);
            }
        }
        return "-1";
    }


    public AmmeterHandleResult processDataParams(String data){
        AmmeterHandleResult result = new AmmeterHandleResult();
        result.setProtocolHander(data.substring(0,2));
        result.setProtocolVersion(data.substring(2,4));
        result.setAmmeterType(data.substring(4,6));
        result.setCommand(data.substring(6,26));
        result.setData(data.substring(26,28));
        result.setCrcValue(data.substring(28));
        //命令转换
//        result.setRealCommnd(AmmeterUtils.exchangeCommand(result.getCommand()));
        return result;
    }

}
