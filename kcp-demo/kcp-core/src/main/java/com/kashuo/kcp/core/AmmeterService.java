package com.kashuo.kcp.core;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.api.entity.CommandParams;
import com.kashuo.kcp.api.entity.callback.DeviceUdpData;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.AmmeterDeviceMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.AmmeterWorkingInfoMapper;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.dao.result.AmmeterDeviceResult;
import com.kashuo.kcp.domain.*;
import com.kashuo.kcp.utils.HttpClientUtils;
import com.kashuo.kcp.utils.HttpUtils;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * Created by dell-pc on 2017/9/7.
 */

@Service
public class AmmeterService {
    private final static Logger logger = LoggerFactory.getLogger(AmmeterService.class);
    @Value("${socket.port}")
    public static Integer SOCKET_PORT ;

    private final String SHIDING_BACKEND_URL ="http://127.0.0.1:8088/ammeter/udpNbiot/receive";

    @Autowired
    private AmmeterWorkingInfoMapper ammeterWorkingInfoMapper;

    @Autowired
    private AmmeterDeviceMapper ammeterDeviceMapper;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private AmmeterReportServer reportServer;
    @Autowired
    private NetWorkService netWorkService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private AmmeterPositionMapper positionMapper;


    public List<AmmeterDeviceResult> checkAmmeterMeterNo(){
         return ammeterDeviceMapper.checkAmmeterMeterNo();
    }

    public List<AmmeterDeviceResult> validAmmeterDevice(){
        return ammeterDeviceMapper.validAmmeterDevice();
    }

    public Results updateAmmeterStatus(Integer status, Integer id) throws Exception {
        AmmeterDevice device = ammeterDeviceMapper.selectByPositionId(id);
        AmmeterWorkingInfo ammeterWorkingInfo = ammeterWorkingInfoMapper.selectByAmmeterId(device.getId());
        if(ammeterWorkingInfo != null && (ammeterWorkingInfo.getStatus() == null ||
                (ammeterWorkingInfo.getStatus() != 4 && ammeterWorkingInfo.getStatus() != 3)
               )){
            ammeterWorkingInfoMapper.updateByAmmeterId(device.getId(),status+2);

        }else{
            if( (status == 2 || status == 1) && ammeterDeviceMapper.selectByPrimaryKey(device.getId()) != null) {
                ammeterWorkingInfo = new AmmeterWorkingInfo();
                ammeterWorkingInfo.setAmmeterId(device.getId());
                ammeterWorkingInfo.setStatus(status + 2);
                ammeterWorkingInfoMapper.insert(ammeterWorkingInfo);
            }else{
                return Results.error("正在操作中!");
            }
        }
        //---------开始想IoM平台发送开关闸命令
        CommandParams params = new CommandParams();
        //没有数据发送
        params.setIsChanged("2");
        params.setCommandType(3);
        AmmeterPosition position = positionMapper.selectByPrimaryKey(id);
        String command = status == 1 ? AppConstant.COMMAND_SWTICH_OFF_KEY:AppConstant.COMMAND_SWTICH_ON_KEY;
        commandService.commonCommandSend(position, command, params);
        return Results.success(status == 1 ? "正在拉闸" :"正在合闸");
    }




    public AmmeterDevice selectDeviceByImsi(String imsi){
        return ammeterDeviceMapper.selectByImsiKey(imsi);
    }

    public AmmeterDevice selectByPrimaryKey(Integer id){
        return ammeterDeviceMapper.selectByPrimaryKey(id);
    }


    public Integer updateWarningStatusByPrimaryKey(AmmeterDevice ammeterDevice){
        return ammeterDeviceMapper.updateWarningStatusByPrimaryKey(ammeterDevice);}

    public void insert(AmmeterDevice ammeterDevice){
        ammeterDeviceMapper.insert(ammeterDevice);
    }

    public Integer updateAmmeterDeviceByCondition(AmmeterDevice device){
        return ammeterDeviceMapper.updateByImsiKeySelective(device);
    }


    public Results list(AmmeterCondition ammeterCondition){
        PageHelper.startPage(ammeterCondition.getPageIndex(),ammeterCondition.getPageSize());
         return Results.success(ammeterDeviceMapper.selectByCondition());
    }

    public Results listAmmeterInfo(AmmeterCondition ammeterCondition){
        PageHelper.startPage(ammeterCondition.getPageIndex(),ammeterCondition.getPageSize());
        Page<AmmeterInfoResult> resultPage = ammeterDeviceMapper.selectAmmeterInfo(ammeterCondition);
        Results results = new Results(resultPage);
        if(resultPage != null)
        results.setTotal(resultPage.getTotal());
        results.setSn(ammeterCondition.getSn());
        return results;
    }

    public  String getSocketRequestParam(AmmeterDevice ammeterDevice,
                                               AmmeterWorkingInfo ammeterWorkingInfo,
                                        boolean firstResponse){
        if(firstResponse) {
            //截取Imsi
            ammeterDevice.setImsi(ammeterDevice.getInputMsg().substring(5,20));
            ammeterDevice.setAmmeterNumber(ammeterDevice.getInputMsg().substring(24,36));
            AmmeterDevice device = ammeterDeviceMapper.selectByImsiKey(ammeterDevice.getImsi());
            if(device != null){
                //--------------------
                AmmeterWorkingInfo info = ammeterWorkingInfoMapper.selectByAmmeterId(device.getId());
                //接受network信息插入数据库  RSSI:-50+RSRQ:-70+CELLID:124
                insertNetWorkParams(device,ammeterDevice.getInputMsg());

                if(info != null) {
                    int workInfoIndex = info.returnWorkInfoIndex(info);
                    if (workInfoIndex == 29 &&info.getStatus() != null &&  info.getStatus() == 3) {
//                        ammeterWorkingInfoMapper.updateByAmmeterId(info.getAmmeterId(), info.getStatus() - 2);
                        return sysDictionaryService.getDynamicValue(workInfoIndex,device.getAmmeterNumber());
                    } else if (workInfoIndex == 33 &&info.getStatus() != null &&  info.getStatus() == 4) {
                        System.out.println("=====================================");
//                        ammeterWorkingInfoMapper.updateByAmmeterId(info.getAmmeterId(), info.getStatus() - 2);
                        return sysDictionaryService.getDynamicValue(workInfoIndex,device.getAmmeterNumber());
                    }
                }
                //上一小时电量参数
                AmmeterReport report = new AmmeterReport();
                report.setAmmeterId(device.getId());
                String reportParams = reportServer.sendReponseParamsForReport(report,device.getAmmeterNumber());
                if(!"-1".equals(reportParams)){
                    return reportParams;
                }
                int deviceIndex = ammeterDevice.returnParamIndex(device);
                if(deviceIndex != -1){

                    return sysDictionaryService.getDynamicValue(deviceIndex,device.getAmmeterNumber());
                }else{
                    if(info != null) {
                        int workInfoIndex = info.returnWorkInfoIndex(info);
                        if(workInfoIndex == 29 && info.getStatus() != null && info.getStatus() ==3 ){
                            System.out.println("=====================================");
//                            ammeterWorkingInfoMapper.updateByAmmeterId(info.getAmmeterId(),info.getStatus()-2);
                            return sysDictionaryService.getDynamicValue(workInfoIndex,device.getAmmeterNumber());
                        }else if(workInfoIndex == 33 && info.getStatus() != null && info.getStatus() == 4 ) {
                            System.out.println("=====================================");
//                            ammeterWorkingInfoMapper.updateByAmmeterId(info.getAmmeterId(), info.getStatus() - 2);
                            return sysDictionaryService.getDynamicValue(workInfoIndex,device.getAmmeterNumber());
                        }
                        return sysDictionaryService.getDynamicValue(workInfoIndex,device.getAmmeterNumber());
                    }else {
                        return sysDictionaryService.getDynamicValue(1,device.getAmmeterNumber());
                    }
                }
            }else{
                ammeterDeviceMapper.insert(ammeterDevice);
                AmmeterReport report = new AmmeterReport();
                report.setAmmeterId(ammeterDevice.getId());
                //接受network信息插入数据库  RSSI:-50+RSRQ:-70+CELLID:124
                insertNetWorkParams(ammeterDevice,ammeterDevice.getInputMsg());
                String reportParams = reportServer.sendReponseParamsForReport(report,ammeterDevice.getAmmeterNumber());
                if(!"-1".equals(reportParams)){
                    return reportParams;
                }
                return sysDictionaryService.getDynamicValue(13,ammeterDevice.getAmmeterNumber());
            }
        }else{
            //无线参数
            String netWorkStr = netWorkService.getNetWorkResponseByAmmeterId(ammeterDevice.getId(),ammeterDevice.getAmmeterNumber());
            if(!"-1".equals(netWorkStr)){
                return netWorkStr;
            }
            int infoIndex = ammeterWorkingInfo.returnWorkInfoIndex(ammeterWorkingInfo);
            if(infoIndex == 29 && ammeterWorkingInfo != null&& ammeterWorkingInfo.getStatus() != null && ammeterWorkingInfo.getStatus() ==3){
//                ammeterWorkingInfoMapper.updateByAmmeterId(ammeterWorkingInfo.getAmmeterId(),ammeterWorkingInfo.getStatus()-2);
                return sysDictionaryService.getDynamicValue(infoIndex,ammeterDevice.getAmmeterNumber());
            }else if(infoIndex == 33 &&ammeterWorkingInfo != null&& ammeterWorkingInfo.getStatus() != null &&  ammeterWorkingInfo.getStatus() == 4) {
//                ammeterWorkingInfoMapper.updateByAmmeterId(ammeterWorkingInfo.getAmmeterId(), ammeterWorkingInfo.getStatus() - 2);
                return sysDictionaryService.getDynamicValue(infoIndex,ammeterDevice.getAmmeterNumber());
            }
            AmmeterReport report = new AmmeterReport();
            report.setAmmeterId(ammeterDevice.getId());
            String reportParams = reportServer.sendReponseParamsForReport(report,ammeterDevice.getAmmeterNumber());
            if(!"-1".equals(reportParams)){
                return reportParams;
            }
            int deviceIndex = ammeterDevice.returnParamIndex(ammeterDevice);
            if(deviceIndex != -1){

                return sysDictionaryService.getDynamicValue(deviceIndex,ammeterDevice.getAmmeterNumber());
            }else{
                if(infoIndex != -1)
                return sysDictionaryService.getDynamicValue(infoIndex,ammeterDevice.getAmmeterNumber());
            }

        }
        return "-1";
    }



    @Transactional
    public String updateAmmeterDevice(AmmeterDevice ammeterDevice) {
        String response ="-1";
        SysDictionaryService sysService = new SysDictionaryService();
        List<SysDictionary> list = sysService.getDictionartLists();
        String[] results = ammeterDevice.getInputMsg().split("\\+");
        if( ammeterDevice.getInputMsg()!= null && ammeterDevice.getInputMsg().startsWith("IMSI:")){
          return   getSocketRequestParam(ammeterDevice,null,true);
        }else if(ammeterDevice.getInputMsg() == null){
            return response;
        }
        ammeterDevice.setAmmeterNumber(results[0].substring(3));
        if(list != null) {
            for (int i = 1; i <= list.size(); i++) {
                SysDictionary sysDictionary = sysService.getDictionaryByIndex(i);
                logger.info("dictionary :"+i+": "+sysDictionary.getParamId()+" 当前inputMsg:"+ammeterDevice.getInputMsg());
                if (ammeterDevice.getInputMsg().contains(sysService.getDynamicValue(sysDictionary.getParamId(),ammeterDevice.getAmmeterNumber()))) {

                    //截取AmmeterNumber
                    ammeterDevice.setAmmeterNumber(results[0].substring(3));
                    AmmeterDevice device = ammeterDeviceMapper.selectByAmmeterNumber(ammeterDevice.getAmmeterNumber());
                    String params = ammeterDevice.getInputMsg().substring(
                            ammeterDevice.getInputMsg().indexOf("+")+1, ammeterDevice.getInputMsg().length());



                    //无线参数判断
                    if(ammeterDevice.getInputMsg().contains("RSSI:")){
//                        netWorkService.insertNetWorkInfo(device,params);
                        return getSocketRequestParam(device,ammeterWorkingInfoMapper.selectByAmmeterId(device.getId()),false);
                    }
                    String descMsg ="";
                    if(results[2].contains("ERR:")){
                        descMsg = results[2].substring(3);
                    }
                    String result = results[2].substring(3);
                    //set 参数
                    if(sysDictionary.getParamId() > 12 && sysDictionary.getParamId() != 29 && sysDictionary.getParamId() != 33) {
                        //更新电表固有参数

                        if (device != null) {
                            //更新电表固有信息表
                            if(!"".equals(descMsg)){
                                ammeterDevice.setDescMsg(descMsg);
                            }else {
                                device.setParams(result, sysDictionary.getParamId(), device);
                            }
                            ammeterDeviceMapper.updateByImsiKeySelective(device);
                            return getSocketRequestParam(device,ammeterWorkingInfoMapper.selectByAmmeterId(device.getId()),false);
                        } else {
                            //插入电表固有信息表
                            if(!"".equals(descMsg)){
                                ammeterDevice.setDescMsg(descMsg);
                            }else {
                                ammeterDevice.setParams(result, sysDictionary.getParamId(), ammeterDevice);
                            }
                            ammeterDeviceMapper.insert(ammeterDevice);
                            return getSocketRequestParam(ammeterDevice,ammeterWorkingInfoMapper.selectByAmmeterId(ammeterDevice.getId()),false);
                        }
                    }else{
                        //更新电表工作参数
                        AmmeterWorkingInfo ammeterWorkingInfo = new AmmeterWorkingInfo();
                        ammeterWorkingInfo.setParams(result,sysDictionary.getParamId(),ammeterWorkingInfo);
                        if (device != null) {
                            ammeterWorkingInfo.setAmmeterId(device.getId());
                            AmmeterWorkingInfo info = ammeterWorkingInfoMapper.selectByAmmeterId(device.getId());
                            if(info != null){
                                if(!"".equals(descMsg)){
                                    info.setDescMsg(descMsg);
                                }else {
                                    info.setParams(result,sysDictionary.getParamId(),info);
                                }
                                ammeterWorkingInfoMapper.updateByAmmeterIdSelective(info);
                            }else{
                                ammeterWorkingInfo.setAmmeterId(device.getId());
                                if(!"".equals(descMsg)){
                                    ammeterWorkingInfo.setDescMsg(descMsg);
                                }else {
                                    ammeterWorkingInfo.setParams(result,sysDictionary.getParamId(),ammeterWorkingInfo);
                                }
                                ammeterWorkingInfoMapper.insert(ammeterWorkingInfo);
                                info = ammeterWorkingInfo;
                            }
                            if(sysDictionary.getParamId() == 11 ){
                                reportServer.insertDailyReportServer(device,result);
                            }
                            return getSocketRequestParam(device,info,false);
                        }else{
                            //插入固有信息 和工作状态参数表
                            ammeterDeviceMapper.insert(ammeterDevice);
                            ammeterWorkingInfo.setAmmeterId(ammeterDevice.getId());
                            ammeterWorkingInfoMapper.insert(ammeterWorkingInfo);
                            if(sysDictionary.getParamId() == 11){
                                reportServer.insertDailyReportServer(ammeterDevice,result);
                            }
                            return getSocketRequestParam(ammeterDevice,ammeterWorkingInfo,false);
                        }
                    }
                }
            }
            return response;
        }else{
            return "-1";
        }
    }

    public void insertNetWorkParams(AmmeterDevice device,String param){
        if(param.length() > 20 && param.contains("RSSI")) {
//            netWorkService.insertNetWorkInfo(device, param.substring(21));
        }
    }

    public String postUDPDeviceMessage(AmmeterDevice device){
        String[] datas = device.getInputMsg().split(",");
        DeviceUdpData data = new DeviceUdpData();
        data.setData(device.getInputMsg());
        if(datas.length >13) {
            data.setImei(datas[12]);
        }
        data.setNotifyType("UDP_Nb_Iot");
        try {
            HttpClientUtils.getDataFromPostMethod(SHIDING_BACKEND_URL, JSONObject.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
            return "-1";
        }
        return "OK";
    }


    public static void main(String[] args) {
        String str ="460001357924680+0x2800001+120.223";
        String info ="ID:112233445566+CMD:68112233445566681c1033333333ab8967454d3345634a34384c3816+OK:6886200500000068910833333433A876B733E916";
        System.out.println(info.split("\\+")[2].substring(3));
    }
}
