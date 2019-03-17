package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.AmmeterRuleService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterRuleMapper;
import com.kashuo.kcp.dao.condition.AmmeterSystemParams;
import com.kashuo.kcp.dao.condition.AmmeterWellCoverSystemParams;
import com.kashuo.kcp.domain.AmmeterMsgContact;
import com.kashuo.kcp.domain.AmmeterRule;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.manage.DeviceConfigService;
import com.kashuo.kcp.redis.RedisService;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtil;
import com.kashuo.kcp.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2018/4/15.
 */
@RestController
@Api("系统参数管理")
@RequestMapping("/system")
public class SystemParamsController extends BaseController {

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterRuleService ruleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private DeviceConfigService deviceConfigService;

    @PostMapping("/list")
    @ApiOperation(value = "系统参数列表")
    public Results list(@RequestBody AmmeterSystemParams params){
        List<SysDictionary> sysDictionaryList = sysDictionaryService.getSystemParamsList();
        return Results.success(sysDictionaryList,params.getSn());
    }

    @ApiOperation("更新系统参数列表")
    @PostMapping("/update")
    public Results update(@RequestBody AmmeterSystemParams params){
        sysDictionaryService.updateSystemParams(params);

        return  Results.success("更新成功!",params.getSn());
    }

    @ApiOperation("更新井盖参数阀值")
    @PostMapping("/updateWellCover")
    public Results updateWellCover(@RequestBody AmmeterWellCoverSystemParams wellCoverSystemParams){
        if(!isAdmin(getCuruser().getChannelId())){
            return Results.error("请联系管理员进行操作!");
        }
       ruleService.saveWellCoverRule(wellCoverSystemParams);
       return Results.success("更新成功!");
    }

    @ApiOperation("获取井盖参数阀值列表")
    @GetMapping("/getWellCoverList")
    public Results WellCoverParamsList(){
        Map<String,Object> params = new HashMap<>();
        List<AmmeterRule> rules = ruleService.getDictionartLists();
        for (AmmeterRule rule:rules
             ) {
            params.put(rule.getRuleParams(),rule);
        }
        return Results.success(params);
    }

    @ApiOperation("获取短信推送联系人")
    @GetMapping("/getMsgContactInfo")
    public Results getMsgContactInfo(@RequestParam(required = false) Integer projectId){
        AmmeterUser user = getCuruser();
        AmmeterMsgContact contact = deviceConfigService.getMsgFromCache(user,projectId);
        if(contact == null){
            contact = new AmmeterMsgContact();
        }
        return Results.success(contact);
    }
    @PostMapping("/updateMsgContactInfo")
    public Results updateMsgInfo(@RequestBody AmmeterMsgContact contact){
        if(StringUtil.isEmpty(contact.getContactName1())){
            return Results.error("第一联系人姓名不能为空!");
        }
        if(StringUtil.isEmpty(contact.getContactPhone1())){
            return Results.error("第一联系人联系号码不能为空!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone1()) && !ValidateUtil.validatePhoneNumber(String.valueOf(contact.getContactPhone1()))){
            return Results.error("第一联系人联系号码不合法!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone2())&& !ValidateUtil.validatePhoneNumber(String.valueOf(contact.getContactPhone2()))){
            return Results.error("第二联系人联系号码不合法!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone3()) && !ValidateUtil.validatePhoneNumber(String.valueOf(contact.getContactPhone3()))){
            return Results.error("第三联系人联系号码不合法!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone1()) && StringUtil.isNotEmpty(contact.getContactPhone2()) &&
                contact.getContactPhone1().trim().equals(contact.getContactPhone2().trim())){
            return Results.error("第一联系人联系号码不可以和第二联系人联系号码相同!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone1()) && StringUtil.isNotEmpty(contact.getContactPhone3()) &&
                contact.getContactPhone1().trim().equals(contact.getContactPhone3().trim())){
            return Results.error("第一联系人联系号码不可以和第三联系人联系号码相同!");
        }
        if(StringUtil.isNotEmpty(contact.getContactPhone2())&& StringUtil.isNotEmpty(contact.getContactPhone3()) &&
                contact.getContactPhone2().trim().equals(contact.getContactPhone3().trim())){
            return Results.error("第二联系人联系号码不可以和第三联系人联系号码相同!");
        }
        AmmeterUser user = getCuruser();
        contact.setChannelId(user.getChannelId());
        if(contact.getProjectId() == null){
            contact.setProjectId(1);
        }
        AmmeterMsgContact cacheContact = deviceConfigService.getMsgFromCache(user,contact.getProjectId());

        if(cacheContact != null){
            contact.setId(cacheContact.getId());
            deviceConfigService.updateMsgInfoByCondition(contact);
        }else{
            contact.setChannelId(user.getChannelId());
            contact.setProjectId(1);
            deviceConfigService.insertMsgInfo(contact);
        }
        redisService.set(AppConstant.NB_CONTACTINFO+"_"+user.getChannelId()+"_"+contact.getProjectId(),JSONObject.toJSONString(contact));
        return Results.success("保存联系人成功!");
    }

    @PostMapping("/addBlackListImei")
    @ApiOperation("添加Imei黑名单")
    public Results addBlackListByImei(@RequestBody String imei){
        String blackListStr = redisService.get(AppConstant.NB_DEVICE_BLACKLIST);
        if(imei != null) {
            String[] imeiArr = imei.split(",");
            List<String> blackImeis = new ArrayList<>();
            if (blackListStr != null) {
                blackImeis = JSONObject.parseObject(blackListStr, List.class);
            }
            blackImeis = deviceConfigService.addBlackList(imeiArr, blackImeis);
            redisService.set(AppConstant.NB_DEVICE_BLACKLIST, JSONObject.toJSONString(blackImeis));
        }
        return Results.success("添加Imei黑名单成功!");
    }
    @PostMapping("/removeBlackListImei")
    @ApiOperation("添加Imei黑名单")
    public Results removeBlackListByImei(@RequestBody String imei){
        String blackListStr = redisService.get(AppConstant.NB_DEVICE_BLACKLIST);
        if(imei != null) {
            String[] imeiArr = imei.split(",");
            List<String> blackImeis = new ArrayList<>();
            if (blackListStr != null) {
                blackImeis = JSONObject.parseObject(blackListStr, List.class);
            }
            blackImeis = deviceConfigService.removeBlackList(imeiArr, blackImeis);
            redisService.set(AppConstant.NB_DEVICE_BLACKLIST, JSONObject.toJSONString(blackImeis));
        }
        return Results.success("移除Imei黑名单成功!");
    }

}
