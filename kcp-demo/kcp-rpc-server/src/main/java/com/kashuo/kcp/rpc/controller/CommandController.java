package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.utils.JsonUtil;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.AsynCommandDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandInDTO;
import com.huawei.iotplatform.client.dto.PostDeviceCommandOutDTO;
import com.kashuo.kcp.api.entity.AmmeterCommand;
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
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_RESET_CALLBACK_URL, AppConstant.CALLBACK_URLS_TYPE_ID));
        AsynCommandDTO commandDTO = new AsynCommandDTO();
        String command = sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_RESET_KEY, AppConstant.CALLBACK_URLS_TYPE_ID);
        commandDTO.setServiceId(AppConstant.COMMAND_SERVICEID_PARAM_NAME);
        commandDTO.setMethod(AppConstant.COMMAND_METHOD_PARAM_NAME);
        AmmeterCommand ammeterCommand = new AmmeterCommand();
        ammeterCommand.setDown_command(command);
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(ammeterCommand);
        commandDTO.setParas(paras);
        deviceCommandInDTO.setCommand(commandDTO);
        logger.info("命令reset下发 参数:{}", JsonUtil.jsonObj2Sting(deviceCommandInDTO));
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO);
            return Results.success("设备软重启发送成功!",commandCondition.getSn());
        }

        return Results.error("设备软重启发送失败!",commandCondition.getSn());
    }
    @PostMapping("/restore")
    @ApiOperation("设备恢复出厂设置")
    public Results restoreDevice(@RequestBody CommandCondition commandCondition) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(commandCondition.getDeviceId());
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_RESTORE_CALLBACK_URL,
                AppConstant.CALLBACK_URLS_TYPE_ID));
        deviceCommandInDTO = commandService.prepareCommand(AppConstant.COMMAND_RESTORE_KEY,
                AppConstant.COMMAND_RESTORE_CALLBACK_URL,deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO);
            return Results.success("设备恢复出厂设置发送成功!",commandCondition.getSn());
        }
        return Results.error("设备恢复出厂设置发送失败!",commandCondition.getSn());
    }

    @PostMapping("/modifyKeepAlive")
    @ApiOperation("设备保活时间间隔设置")
    public Results keepAliveDevice(@RequestBody CommandCondition commandCondition) throws Exception {
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        deviceCommandInDTO.setDeviceId(commandCondition.getDeviceId());
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_COMMON_CALLBACK_URL,
                AppConstant.CALLBACK_URLS_TYPE_ID));
        deviceCommandInDTO = commandService.prepareCommand(AppConstant.COMMAND_NB_KEEPALIVE_KEY,
                AppConstant.COMMAND_COMMON_CALLBACK_URL,deviceCommandInDTO);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO);
            return Results.success("向设备修改保活间隔设置发送成功!",commandCondition.getSn());
        }
        return Results.error("向设备修改保活间隔设置发送失败!",commandCondition.getSn());
    }

    @PostMapping("/test")
    @ApiOperation("测试命令")
    public Results testIoTCommand(@RequestParam String command) throws Exception {
        logger.info(command+"------------------");
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        AmmeterPosition position = positionService.selectByImei("863703033404885");
        deviceCommandInDTO.setDeviceId(position.getDeviceId());
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_COMMON_CALLBACK_URL,
                AppConstant.CALLBACK_URLS_TYPE_ID));
        deviceCommandInDTO = commandService.prepareCommand(command,
                AppConstant.COMMAND_COMMON_CALLBACK_URL,deviceCommandInDTO,true);
        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
        if(deviceCommandOutDTO != null){
            //插入命令历史记录
            commandService.insertCommandHistory(deviceCommandOutDTO);
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
