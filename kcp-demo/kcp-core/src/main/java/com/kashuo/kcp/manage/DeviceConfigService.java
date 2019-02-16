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
import com.kashuo.kcp.entity.MessageBody;
import com.kashuo.kcp.redis.RedisService;
import com.kashuo.kcp.utils.MessageNewUtils;
import com.kashuo.kcp.utils.MessageUtils;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.ValidateUtil;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                AmmeterUser user = new AmmeterUser();
                user.setChannelId(position.getChannelId());
                AmmeterMsgContact cacheContact = getMsgFromCache(user, projectId);
                if(isBlackImei(position.getImei())){
                    return;
                }
                if(cacheContact != null){
                    String phone="";
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone1()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone1())){
                        //sendMsgByLimit(position,status,cacheContact.getContactPhone1());
                        phone= cacheContact.getContactPhone1();
                    }
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone2()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone2())){
//                        sendMsgByLimit(position,status,cacheContact.getContactPhone2());
                        phone +=cacheContact.getContactPhone2();
                    }
                    if(StringUtil.isNotEmpty(cacheContact.getContactPhone3()) && ValidateUtil.validatePhoneNumber(cacheContact.getContactPhone3())){
//                        sendMsgByLimit(position,status,cacheContact.getContactPhone3());
                        phone +=cacheContact.getContactPhone3();
                    }
                    sendMsgByLimit(position,status,phone);
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
        if(number >30){
            return;
        }
        MessageBody message = new MessageBody();
        message.setImei(position.getImei());
        message.setAddress(position.getAddress());
        message.setStatus(status);
        message.setRemark("请登录网站查看");
        boolean flag =MessageNewUtils.sendNoticesMessage(message,phone);
//        boolean flag = MessageUtils.sendLargeMessage(position.getImei(), status,phone,
//                position.getName(), position.getAddress());
        if(flag){
            redisService.set(AppConstant.NB_CONTACTINFO+"_"+phone,String.valueOf(number+1));
            redisService.expireKey(AppConstant.NB_CONTACTINFO+"_"+phone,12, TimeUnit.HOURS);
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

    public List<String> addBlackList(String[] imeiArr,List<String> blackImeis) {
        for (String imeiStr :imeiArr) {
            boolean inBlack =false;
            for (String blackImei:blackImeis) {
                if(imeiStr.equals(blackImei)){
                    inBlack =true;
                    break;
                }
            }
            if(!inBlack){
                blackImeis.add(imeiStr) ;
            }
        }

        return blackImeis;
    }

    public List<String> removeBlackList(String[] imeiArr,List<String> blackImeis) {
        for(int i =blackImeis.size()-1;i>=0;i--){
            boolean inBlack =false;
            for (String imeiStr :imeiArr) {
                if(imeiStr.equals(blackImeis.get(i))){
                    inBlack =true;
                    break;
                }
            }
            if(inBlack){
                blackImeis.remove(i) ;
            }
        }
        return blackImeis;
    }

    public boolean isBlackImei(String imei){
        String blackListStr = redisService.get(AppConstant.NB_DEVICE_BLACKLIST);
        List<String> blackImeis;
        boolean blackFlag = false;
        if(blackListStr != null){
            blackImeis = JSONObject.parseObject(blackListStr,List.class);
            for (String blackImei:blackImeis) {
                if(blackImei.equals(imei)){
                    blackFlag =true;
                }
            }
        }

        return blackFlag;
    }




}
