package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.utils.JsonUtil;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AsynCommandDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandInDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandOutDTO;
import com.kashuo.kcp.api.entity.AmmeterCommand;
import com.kashuo.kcp.api.entity.CommandParams;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.condition.CommandCondition;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dell-pc on 2018/4/21.
 */
@RestController
@RequestMapping("/command")
@Api("信令传送")
public class CommandController {

    private Logger logger = LoggerFactory.getLogger(CommandController.class);

    @Autowired
    private CommandService commandService;
    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private AmmeterPositionService positionService;

    @PostMapping("/reset")
    @ApiOperation("设备软重启")
    public Results resetAmmeter(@RequestBody CommandCondition commandCondition) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(commandCondition.getDeviceId());
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_RESET_KEY);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
        params.setCommandType(1);
        params.setData(AppConstant.COMMAND_RESET_KEY.toUpperCase());
        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
            return Results.success("设备软重启发送成功!",commandCondition.getSn());
        }
        return Results.error("设备软重启发送失败!",commandCondition.getSn());
    }
    @PostMapping("/restore")
    @ApiOperation("设备恢复出厂设置")
    public Results restoreDevice(@RequestBody CommandCondition commandCondition) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(commandCondition.getDeviceId());
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_RESTORE_KEY);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
        params.setData(AppConstant.COMMAND_RESTORE_KEY.toUpperCase());
        params.setCommandType(1);
        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(),
                            sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID)));
            return Results.success("设备恢复出厂设置发送成功!",commandCondition.getSn());
        }
        return Results.error("设备恢复出厂设置发送失败!",commandCondition.getSn());
    }

    @PostMapping("/modifyKeepAlive")
    @ApiOperation("设备保活时间间隔设置")
    public Results keepAliveDevice(@RequestBody CommandCondition commandCondition) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(commandCondition.getDeviceId());
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_NB_KEEPALIVE_KEY);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        params.setDataFlag(true);
        params.setData(commandCondition.getKeepTime());
        params.setCommandType(1);
        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO,
                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
            return Results.success("向设备修改保活间隔设置发送成功!",commandCondition.getSn());
        }
        return Results.error("向设备修改保活间隔设置发送失败!",commandCondition.getSn());
    }

    @PostMapping("/test")
    @ApiOperation("测试命令")
    public Results testIoTCommand(@RequestParam String command,@RequestParam String data,@RequestParam Integer electric) throws Exception {
        logger.info(command+"------------------");
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        AmmeterPosition position = positionService.selectByImei("863703033404885");
        deviceCommandInDTO.setDeviceId(position.getDeviceId());
        CommandParams params = new CommandParams();
        params.setCommandKey(command);
        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
        if(data != null && !"1".equals(data.trim())) {
            params.setData(data);
        }else{
            params.setIsChanged("1");
        }
        params.setDataFlag(true);
        params.setCommandType(electric);
        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO,true);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO,command);
            return Results.success("向设备发送测试命令成功!","测试");
        }
        return Results.error("向设备发送测试命令失败!","测试");
    }

    public static void main(String[] args) throws Exception {
        AmmeterCommand ammeterCommand = new AmmeterCommand();
        ammeterCommand.setDown_command("89010106STM00105RESET119E");
        System.out.println(JsonUtil.convertObject2ObjectNode(ammeterCommand));
    }
}
