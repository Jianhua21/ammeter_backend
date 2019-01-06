package com.kashuo.kcp.core;

import cmcciot.onenet.nbapi.sdk.api.command.NbiotDeviceManagement;
import cmcciot.onenet.nbapi.sdk.entity.Device;
import cmcciot.onenet.nbapi.sdk.entity.NbiotResult;
import com.alibaba.fastjson.JSONObject;
import com.huawei.iotplatform.client.dto.PostDeviceCommandOutDTO;
import com.kashuo.kcp.api.entity.CommandParams;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.domain.AmmeterNbiot;
import com.kashuo.kcp.domain.AmmeterPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dell-pc on 2018/10/25.
 */
public class CommandSendThread implements Runnable
{
    private Logger logger = LoggerFactory.getLogger(CommandSendThread.class);
    private int delaySecond = 10000;// 10秒钟轮询一次
    private int retryTime = 5;// 重试次数
    private AmmeterNbiot nbiot;
    private AmmeterPosition position;
    private String  command;
    private  CommandService commandService;
    private SysDictionaryService sysDictionaryService;
    public CommandSendThread(AmmeterNbiot nbiot,AmmeterPosition position,String  command,CommandService commandService,
                             SysDictionaryService sysDictionaryService){
        this.nbiot =nbiot;
        this.command=command;
        this.position=position;
        this.commandService=commandService;
        this.sysDictionaryService=sysDictionaryService;
    }

    @Override
    public void run() {
        logger.info("nbiot 尝试 命令 "+command+" 发送");
        Device device = new Device("",position.getImei(),position.getNumber());
        device.setCommand(command);
        device.setWriteResId(nbiot.getResourceId());
        device.setObjId(nbiot.getObjId());
        device.setObjInstId(nbiot.getObjInstanceId());
        NbiotDeviceManagement deviceManagement = new NbiotDeviceManagement(nbiot.getApiKey());
        Integer times =0;
        PostDeviceCommandOutDTO commandOutDTO = new PostDeviceCommandOutDTO();
        try {
            NbiotResult result = deviceManagement.sendWriteCommand(device);
            logger.info("Nb Iot返回结果:{}"+JSONObject.toJSONString(result));
            boolean code = "5106".equals(result.getErrno());
            while (times < retryTime && code) {
                logger.info("nbiot 尝试第:" + (times + 1) + "次重新发送");
                System.out.println("nbiot 尝试第:" + (times + 1) + "次重新发送");
                result = deviceManagement.sendWriteCommand(device);
                logger.info("nbiot 返回数据:" + JSONObject.toJSONString(result));
                if ("5106".equals(result.getErrno())) {
                    times++;
                    Thread.sleep(delaySecond);
                } else {
                    break;
                }
            }
            commandOutDTO.setDeviceId(position.getDeviceId());
            commandOutDTO.setAppId("Nb_Iot");
            commandOutDTO.setStatus(result.getErrno());
            commandOutDTO.setCommandId(command);
        }catch(Exception e){
            logger.error("发送失败!");
        }
        if(commandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(commandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(command,
                            sysDictionaryService.getDynamicSystemValue(command, AppConstant.CALLBACK_URLS_TYPE_ID)));
        }
    }
}
