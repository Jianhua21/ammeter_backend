package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.eums.DeviceTypes;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by dell-pc on 2019/3/19.
 */
@RequestMapping("/data")
@RestController
public class DataDemoController {

    private static Logger logger = LoggerFactory.getLogger(DataDemoController.class);

    @Autowired
    private AmmeterPositionService positionService;

    @Autowired
    private AmmeterService ammeterService;

    @Value("${app.constant.gaode.appId}")
    private String key;

    @RequestMapping(value = "/batchInsert",method = RequestMethod.GET)
    public Results batchInsert(@RequestParam Integer number){

        if(number < 1) number =1;
        String imePrefix ="867003039878";
        for (int i =0 ;i < number ; i++) {
            AmmeterPosition position = new AmmeterPosition();
            if(i< 10){
                position.setImei(imePrefix+"00"+i);
                position.setNumber(position.getImei());
            }else if(number < 100){
                position.setImei(imePrefix+"0"+i);
                position.setNumber(position.getImei());
            }else {
                position.setImei(imePrefix+i);
                position.setNumber(position.getImei());
            }
            position.setName("闵行区交大-井盖-"+i);
            position.setGpsLongitude("121.433289");
            position.setGpsLatitude("31.199759");
            positionService.setAmapLocation(position,key);
            position.setAddress(position.getAddress());
            position.setInstaller("-");
            position.setDeviceType(1);
            position.setProductId(DeviceTypes.parseName(String.valueOf(position.getDeviceType())));
            position.setStatus(0);
            position.setCreateTime(new Date());
            position.setCreateBy(13);
            position.setChannelId(11);
            position.setPlatform(2);
            logger.info("Position 信息:{}", JSON.toJSONString(position));
            positionService.insert(position);

            AmmeterDevice ammeterDevice = new AmmeterDevice();
            ammeterDevice.setAmmeterNumber(position.getNumber());
            ammeterDevice.setImsi(position.getImei());
            ammeterDevice.setAddress(position.getAddress());
            ammeterDevice.setProductDate(new Date());
            ammeterDevice.setProtocolNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL+"/"+
                    AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_PROTOCOL);
            ammeterDevice.setType(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_TYPE);
            ammeterDevice.setHardwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_ID);
            ammeterDevice.setSoftwareNo(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MANUFACTURER_NAME);
            ammeterDevice.setAssManageCode(AppConstant.AmmeterBaseInformation.AMMETER_DEVICE_MODEL);
            ammeterDevice.setChannelId(11);
            logger.info("AmmeterDevice 信息:{}", JSON.toJSONString(ammeterDevice));
            ammeterService.insert(ammeterDevice);

        }

        return Results.success("Batch Insert成功!");
    }
}
