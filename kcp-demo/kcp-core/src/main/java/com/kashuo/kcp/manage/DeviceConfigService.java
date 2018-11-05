package com.kashuo.kcp.manage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.AmmeterConfigMapper;
import com.kashuo.kcp.dao.AmmeterMsgContactMapper;
import com.kashuo.kcp.dao.AmmeterMsgHistoryMapper;
import com.kashuo.kcp.dao.AmmeterUserMapper;
import com.kashuo.kcp.domain.AmmeterConfig;
import com.kashuo.kcp.domain.AmmeterMsgContact;
import com.kashuo.kcp.domain.AmmeterMsgHistory;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.redis.RedisService;
import com.kashuo.kcp.utils.MessageUtils;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.ValidateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by dell-pc on 2018/5/8.
 */
@Service
public class DeviceConfigService {

    @Autowired
    private AmmeterConfigMapper configMapper;

    @Autowired
    private AmmeterMsgContactMapper msgContactMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private AmmeterUserMapper userMapper;

    @Autowired
    private AmmeterMsgHistoryMapper msgHistoryMapper;

    public AmmeterConfig selectByPositionId(Integer positionId){
        return configMapper.selectByPositionId(positionId);
    }

    public Integer insertConfig(AmmeterConfig config){
        return configMapper.insertSelective(config);
    }

    public Integer updateConfig(AmmeterConfig config){
        return configMapper.updateByPrimaryKeySelective(config);
    }

    public AmmeterMsgContact getMsgInfoByCondition(Integer channelId,Integer projectId){
        return msgContactMapper.getMsgInfoByCondition(channelId,projectId);
    }

    public Integer updateMsgInfoByCondition(AmmeterMsgContact contact){
        return  msgContactMapper.updateByPrimaryKeySelective(contact);
    }
    public Integer insertMsgInfo(AmmeterMsgContact contact){
        return  msgContactMapper.insert(contact);
    }

    public AmmeterMsgContact getMsgFromCache(AmmeterUser user, Integer projectId){
        if(projectId == null){
            projectId =1;
        }
        String msg = redisService.get(AppConstant.NB_CONTACTINFO+"_"+user.getChannelId()+"_"+projectId);
        AmmeterMsgContact cacheContact ;
        if(msg != null){
            cacheContact = JSON.parseObject(msg,AmmeterMsgContact.class);
        }else{
            cacheContact = getMsgInfoByCondition(user.getChannelId(),projectId);
            if(cacheContact != null) {
                redisService.set(AppConstant.NB_CONTACTINFO + "_" + user.getChannelId() + "_" + projectId, JSONObject.toJSONString(cacheContact));
            }
        }
        return cacheContact;
    }

    public void sendMsgInfoBySMS(AmmeterPosition position,String status,Integer projectId){

        if(position != null) {
            try {
                AmmeterUser user = userMapper.selectByPrimaryKey(position.getCreateBy());
                AmmeterMsgContact cacheContact = getMsgFromCache(user, projectId);
                if(cacheContact != null){
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone1()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone1())){
                        sendMsgByLimit(position,status,cacheContact.getContactPhone1());
                    }
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone2()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone2())){
                        sendMsgByLimit(position,status,cacheContact.getContactPhone2());
                    }
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone3()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone3())){
                        sendMsgByLimit(position,status,cacheContact.getContactPhone3());
                    }
                }else if(StringUtil.isNotEmpty(position.getContactInfo()) && ValidateUtil.validatePhoneNumber(position.getContactInfo())){
                    sendMsgByLimit(position,status,position.getContactInfo());
                }

            }catch (Exception e){

            }
        }

    }
    private void sendMsgByLimit(AmmeterPosition position,String status,String phone){
        String times = redisService.get(AppConstant.NB_CONTACTINFO+"_"+phone);
        int number = 0;
        try{
            number = Integer.parseInt(times);
        }catch (Exception e){

        }
        if(number >10){
            return;
        }
        boolean flag = MessageUtils.sendLargeMessage(position.getImei(), status,phone,
                position.getName(), position.getAddress());
        if(flag){
            redisService.set(AppConstant.NB_CONTACTINFO+"_"+phone,String.valueOf(number+1));
            if(number == 0){
                redisService.expire(AppConstant.NB_CONTACTINFO+"_"+phone,60*60*24*1000);
            }
        }
        sendMsgHistory(position.getImei(),phone,flag,status);
    }


    private void sendMsgHistory(String imei,String phone,boolean flag,String status){
        AmmeterMsgHistory msgHistory = new AmmeterMsgHistory();
        msgHistory.setImei(imei);
        msgHistory.setSendDate(new Date());
        msgHistory.setSendFlag(flag?"1":"0");
        msgHistory.setSendStatus(status);
        msgHistory.setPhone(phone);
        msgHistoryMapper.insert(msgHistory);
    }




}
