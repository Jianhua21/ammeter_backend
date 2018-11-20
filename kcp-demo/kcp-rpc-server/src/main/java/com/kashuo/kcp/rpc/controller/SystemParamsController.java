package com.kashuo.kcp.rpc.controller;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.kashuo.kcp.core.AmmeterRuleService;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterRuleMapper;
import com.kashuo.kcp.dao.AmmeterWellcoverMapper;
import com.kashuo.kcp.dao.condition.AmmeterSystemParams;
import com.kashuo.kcp.dao.condition.AmmeterWellCoverSystemParams;
import com.kashuo.kcp.domain.AmmeterRule;
import com.kashuo.kcp.domain.AmmeterWellcover;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        Map<String,Object> params = new HashedMap();
        List<AmmeterRule> rules = ruleService.getDictionartLists();
        for (AmmeterRule rule:rules
             ) {
            params.put(rule.getRuleParams(),rule);
        }
        return Results.success(params);
    }


}
