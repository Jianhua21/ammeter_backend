package com.kashuo.kcp.rpc.controller;

import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.condition.AmmeterSystemParams;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/15.
 */
@RestController
@Api("系统参数管理")
@RequestMapping("/system")
public class SystemParamsController {

    @Autowired
    private SysDictionaryService sysDictionaryService;

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

}
