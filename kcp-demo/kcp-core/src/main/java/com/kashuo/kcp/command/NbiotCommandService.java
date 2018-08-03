package com.kashuo.kcp.command;

import cmcciot.onenet.nbapi.sdk.api.online.CreateDeviceOpe;
import cmcciot.onenet.nbapi.sdk.api.online.ReadOpe;
import cmcciot.onenet.nbapi.sdk.api.online.WriteOpe;
import cmcciot.onenet.nbapi.sdk.entity.Device;
import cmcciot.onenet.nbapi.sdk.entity.NbiotResult;
import cmcciot.onenet.nbapi.sdk.entity.Read;
import cmcciot.onenet.nbapi.sdk.entity.Write;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.utils.JsonUtil;
import com.kashuo.kcp.api.entity.AmmeterCommand;
import com.kashuo.kcp.api.entity.CommandParams;
import com.kashuo.kcp.api.entity.NbiotDTO;
import com.kashuo.kcp.auth.AuthService;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.constant.NbiotConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterCommandHistoryMapper;
import com.kashuo.kcp.domain.AmmeterNbiot;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.manage.DeviceManageService;
import com.kashuo.kcp.utils.AmmeterUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dell-pc on 2018/8/1.
 */
@Service
public class NbiotCommandService {
    private Logger logger = LoggerFactory.getLogger(NbiotCommandService.class);
    @Autowired
    private AuthService authService;
    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterCommandHistoryMapper commandHistoryMapper;

    @Autowired
    private DeviceManageService deviceManageService;

    @Autowired
    private CommandService commandService;

    @Autowired
    private AmmeterPositionService positionService;

    public void createDevice(AmmeterPosition ammeterPosition) {
        AmmeterNbiot nbiot = authService.getNbiotInformation(ammeterPosition.getProductId());
        CreateDeviceOpe deviceOpe = new CreateDeviceOpe(nbiot.getApiKey());
        Device device = new Device(ammeterPosition.getName(), ammeterPosition.getImei(), ammeterPosition.getNumber());
        device.setDesc(ammeterPosition.getAddress());
        JSONObject info = new JSONObject();
        info.put("version","1.0.0");
        info.put("manu","十钉物联");
        device.setOther(info);
        try {
            String response = deviceOpe.operation(device, device.toJsonObject()).toString();
            NbiotResult result = com.alibaba.fastjson.JSONObject.parseObject(response, NbiotResult.class);
            AmmeterPosition position = new AmmeterPosition();
            position.setId(ammeterPosition.getId());

            if(NbiotConstant.NBIOT_ERROR_CODE_00.equals(result.getErrno())){
                position.setStatus(1);
                com.alibaba.fastjson.JSONObject jsStr = com.alibaba.fastjson.JSONObject.parseObject(result.getData());
                position.setDeviceId(jsStr.getString("device_id"));
            }else{
                position.setStatus(4);
            }
            positionService.updateByPrimaryKeySelective(position);
        }catch (Exception e){

        }
    }



    public void prepareCommand(CommandParams params, NbiotDTO nbiotDTO, Boolean testFlag) throws Exception {
        String command;
        if(testFlag){
            command = params.getCommandKey();
        }else {
            command = sysDictionaryService.getDynamicSystemValue(params.getCommandKey(), AppConstant.CALLBACK_URLS_TYPE_ID);
            //判断是否需要处理645 命令
            if(params.isDltFlag()){
                command = AmmeterUtils.getPackageCommand(params.getAddress(),command);
            }
        }
        AmmeterCommand ammeterCommand = new AmmeterCommand();
        //CRC封装数据
        ammeterCommand.setDown_command(commandService.CRCCommand(command,params));
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(ammeterCommand);
        logger.info("命令{}下发 参数:{}",params.getCommandKey(), JsonUtil.jsonObj2Sting(nbiotDTO));
        AmmeterNbiot nbiot = authService.getNbiotInformation(nbiotDTO.getPrductName());
        if(nbiotDTO.getCommandType() == 1){
            ReadOpe readOperation = new ReadOpe(nbiot.getApiKey());
            Read read = new Read(nbiotDTO.getImei(), nbiot.getObjId());
            read.setObjInstId(nbiot.getObjInstanceId());
            read.setResId(nbiot.getResourceId());
            readOperation.operation(read, null).toString();
        }else if(nbiotDTO.getCommandType() == 2){
            // Write
            WriteOpe writeOpe = new WriteOpe(nbiot.getApiKey());
            Write write = new Write(nbiotDTO.getImei(),  nbiot.getObjId(), nbiot.getObjInstanceId(), nbiotDTO.getWriteMode());
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("res_id", nbiot.getResourceId());
            jsonObject.put("val", ammeterCommand.getDown_command());
            jsonArray.put(jsonObject);
            JSONObject data = new JSONObject();
            data.put("data", jsonArray);
            writeOpe.operation(write, data).toString();
        }
    }
}
