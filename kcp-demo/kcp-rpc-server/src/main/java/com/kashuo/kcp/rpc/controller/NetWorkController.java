package com.kashuo.kcp.rpc.controller;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterReportServer;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.core.NetWorkService;
import com.kashuo.kcp.dao.condition.AmmeterNetWorkCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterNetWorkResult;
import com.kashuo.kcp.domain.AmmeterNetwork;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2017/9/23.
 */
@RestController
@Api(description = "无线信号采集")
@RequestMapping(value = "/network")
public class NetWorkController {

    @Autowired
    private NetWorkService netWorkService;
    @Autowired
    private AmmeterService ammeterService;

    @GetMapping(value = "/list/{imsi}/{queryDate}")
    public Results listNetWorkInfos(@PathVariable("imsi") String imsi, @PathVariable("queryDate") String queryDate){
        Results result = null;
        AmmeterDevice device = ammeterService.selectDeviceByImsi(imsi);
        if(device == null){
            return Results.error("设备不存在");
        }
        AmmeterNetwork networkDB = netWorkService.selectByAmmeterId(device.getId());
        Map<String,Object>  data = new HashMap<>();
        List<Float> dataRsrq = new ArrayList<>();
        if(queryDate.length() == 10) {
            if(DateUtils.stringToDate(queryDate) == null){
                return  Results.error("时间有误!");
            }
            AmmeterNetwork condition = new AmmeterNetwork();
            condition.setAmmeterId(device.getId());
            condition.setRecordDay(queryDate);
            List<AmmeterNetwork> netWorkReport = netWorkService.queryNetWorkParams(condition);
            Map<String, Object> rsrqMap = StringUtils.initDailyReportMap(netWorkService.getMaxHourByReportDate(netWorkReport));
            if (netWorkReport != null) {
                for (AmmeterNetwork network : netWorkReport) {
                    rsrqMap.put(String.valueOf(network.getRecordHour()), network.getRssi());
                }
            }
            for (int i =1; i <=rsrqMap.size(); i++){
                try {
                    dataRsrq.add(Float.parseFloat(String.valueOf(rsrqMap.get(String.valueOf(i-1)))));
                }catch (Exception e){
                    dataRsrq.add(0.0f);
                }
            }
            data.put("rssi",dataRsrq);
            if(networkDB != null){
                data.put("celli",networkDB.getCelli());
                data.put("rsrq",networkDB.getRssi());
            }else{
                data.put("celli","-");
                data.put("rsrq","-");
            }


            result = Results.success(data);
        }else{
            result = Results.error("获取数据失败！");
        }
        return result;
    }
    @ApiOperation(value = "无线信息管理列表")
    @PostMapping(value = "/networkList")
    public Results networkList(@RequestBody AmmeterNetWorkCondition condition){
        Page<AmmeterNetWorkResult> resultPage = netWorkService.networkList(condition);
        if(resultPage != null){
            Results results = new Results(resultPage);
            results.setTotal(resultPage.getTotal());
            return results;
        }
         return Results.success("获取列表异常!");
    }
}
