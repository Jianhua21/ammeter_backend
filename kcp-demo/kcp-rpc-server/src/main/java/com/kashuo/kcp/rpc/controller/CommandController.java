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
import com.kashuo.kcp.api.entity.DeviceCommand;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.condition.CommandCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
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
    @Autowired
    private AmmeterService ammeterService;

    @PostMapping("/reset")
    @ApiOperation("设备软重启")
    public Results resetAmmeter(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null ||position.getDeviceId() == null ){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
//        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
//        deviceCommandInDTO.setDeviceId(position.getDeviceId());
//        CommandParams params = new CommandParams();
//        params.setCommandKey(AppConstant.COMMAND_RESET_KEY);
//        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
//        params.setDataFlag(true);
//        params.setCommandType(1);
//        params.setData(AppConstant.COMMAND_RESET_KEY.toUpperCase());
//        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO);
//        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
//        if(deviceCommandOutDTO != null){
//            //插入命令历史记录
//            commandService.insertCommandHistory(deviceCommandOutDTO,
//                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID));
        CommandParams params = new CommandParams();
        params.setData(AppConstant.COMMAND_RESET_KEY.toUpperCase());
        commandService.commonCommandSend(position,AppConstant.COMMAND_RESET_KEY,params);
        return Results.success("设备软重启发送成功!",commandCondition.getSn());
    }
    @PostMapping("/restore")
    @ApiOperation("设备恢复出厂设置")
    public Results restoreDevice(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null ||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }

//        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
//        deviceCommandInDTO.setDeviceId(position.getDeviceId());
//        CommandParams params = new CommandParams();
//        params.setCommandKey(AppConstant.COMMAND_RESTORE_KEY);
//        params.setCallBackKey(AppConstant.COMMAND_COMMON_CALLBACK_URL);
//        params.setDataFlag(true);
//        params.setData(AppConstant.COMMAND_RESTORE_KEY.toUpperCase());
//        params.setCommandType(1);
//        deviceCommandInDTO = commandService.prepareCommand(params,deviceCommandInDTO);
//        PostDeviceCommandOutDTO deviceCommandOutDTO = commandService.postDeviceCommand(deviceCommandInDTO);
//        if(deviceCommandOutDTO != null){
//            //插入命令历史记录
//            commandService.insertCommandHistory(deviceCommandOutDTO,
//                    sysDictionaryService.getDynamicSystemValue(params.getCommandKey(),
//                            sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID)));
        CommandParams params = new CommandParams();
        params.setData(AppConstant.COMMAND_RESTORE_KEY.toUpperCase());
        commandService.commonCommandSend(position,AppConstant.COMMAND_RESTORE_KEY,params);
        return Results.success("设备恢复出厂设置发送成功!",commandCondition.getSn());
    }

    @PostMapping("/switchOn")
    @ApiOperation("设备开闸")
    public Results switchOnDevice(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        AmmeterDevice device = ammeterService.selectDeviceByImsi(position.getImei());
        if(position == null ||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_SWTICH_ON_KEY);
        //没有数据发送
        params.setDataFlag(true);
        params.setCommandType(3);
        params.setDltFlag(true);
        params.setAddress(device.getMeterNo());
        //下发00数据
        params.setIsChanged("1");
        commandService.commonCommandSend(position,AppConstant.COMMAND_SWTICH_ON_KEY,params);
        return Results.success("开闸命令发送成功!",commandCondition.getSn());
    }


    @PostMapping("/switchOff")
    @ApiOperation("设备关闸")
    public Results switchOffDevice(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null ||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
        AmmeterDevice device = ammeterService.selectDeviceByImsi(position.getImei());
        CommandParams params = new CommandParams();
        params.setCommandKey(AppConstant.COMMAND_SWTICH_ON_KEY);
        //没有数据发送
        params.setDataFlag(true);
        params.setCommandType(3);
        params.setDltFlag(true);
        params.setAddress(device.getMeterNo());
        //下发00数据
        params.setIsChanged("1");
        commandService.commonCommandSend(position,AppConstant.COMMAND_SWTICH_OFF_KEY,params);
        return Results.success("关闸命令发送成功!",commandCondition.getSn());
    }
    @PostMapping("/modifyKeepAlive")
    @ApiOperation("设备保活时间间隔设置")
    public Results keepAliveDevice(@RequestBody CommandCondition commandCondition) throws Exception {
        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
//        CommandParams params = new CommandParams();
//        //发送保活数据
//        params.setData(commandCondition.getKeepTime());
//        commandService.commonCommandSend(position,AppConstant.COMMAND_NB_KEEPALIVE_KEY,params);
        commandService.configKeepAlive(position,commandCondition.getKeepTime());
        return Results.success("向设备修改保活间隔设置发送成功!",commandCondition.getSn());
    }

    @PostMapping("/saveConfig")
    @ApiOperation("保存系统配置")
    public Results saveConfigDevice(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null ||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
        CommandParams params = new CommandParams();
        //只发送配置命令 不发送数据
        params.setIsChanged("2");
        commandService.commonCommandSend(position,AppConstant.COMMAND_SAVE_CONFIG_KEY,params);
        return Results.success("保存系统配置命令发送成功!",commandCondition.getSn());
    }

    @PostMapping("/getDeviceAddress")
    @ApiOperation("获取电表地址")
    public Results getDeviceAddress(@RequestBody CommandCondition commandCondition) throws Exception {

        if(commandCondition.getPositionId() == null){
            return Results.error("设备不存在!");
        }
        AmmeterPosition position = positionService.selectByPrimaryKey(commandCondition.getPositionId());
        if(position == null ||position.getDeviceId() == null){
            return Results.error("设备不存在!");
        }else if(position.getStatus() == 8){
            return Results.error("异常设备,不可操作!");
        }
        //只发送配置命令 不发送数据
        commandService.getAmmeterAddress(position.getDeviceId());
        return Results.success("获取电表地址命令发送成功!",commandCondition.getSn());
    }





    @PostMapping("/test")
    @ApiOperation("测试命令")
    public Results testIoTCommand(@RequestParam String command,@RequestParam String imei) throws Exception {
        logger.info(command+"------------------");
        PostDeviceCommandInDTO deviceCommandInDTO = new PostDeviceCommandInDTO();
        AmmeterPosition position = positionService.selectByImei(imei);
        if(position == null||position.getStatus()==3){
            return Results.error("IMEI不存在!或已被删除!");
        }
        deviceCommandInDTO.setDeviceId(position.getDeviceId());
        AsynCommandDTO commandDTO = new AsynCommandDTO();
        deviceCommandInDTO.setCallbackUrl(sysDictionaryService.getDynamicSystemValue(AppConstant.COMMAND_COMMON_CALLBACK_URL,
                AppConstant.CALLBACK_URLS_TYPE_ID));
        commandDTO.setServiceId("NbiotCommonDevice");
        commandDTO.setMethod("downcommand");
        DeviceCommand ammeterCommand = new DeviceCommand();
        //CRC封装数据
        ammeterCommand.setDownmessage(command);
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(ammeterCommand);
        commandDTO.setParas(paras);
        deviceCommandInDTO.setCommand(commandDTO);
        deviceCommandInDTO.setExpireTime(0);
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
