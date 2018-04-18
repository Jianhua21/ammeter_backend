package com.kashuo.kcp.rpc.controller;

import com.kashuo.kcp.core.AmmeterChannelService;
import com.kashuo.kcp.core.AmmeterReportServer;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.dao.condition.AmmeterUpdateCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterMonthlyReport;
import com.kashuo.kcp.domain.AmmeterReport;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.Results;
import com.kashuo.kcp.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dell-pc on 2017/9/7.
 */

@RestController
@RequestMapping(value = "/device")
@Api(description ="智能电表终端管理")
public class AmmeterController extends BaseController{

    @Autowired
    private AmmeterService ammeterService;
    @Autowired
    private AmmeterReportServer reportServer;
    @Autowired
    private AmmeterChannelService channelService;

    @PostMapping(value = "/updateStatus")
    @ApiOperation(value="电表开闸合闸操作")
    public Results updateAmmeterStatus(@RequestBody AmmeterUpdateCondition condition){
        return ammeterService.updateAmmeterStatus(condition.getStatus(),condition.getId());
    }

    @PostMapping(value = "/list")
    @ApiOperation(value="电表信息列表")
    public Results list(@RequestBody AmmeterCondition ammeterCondition ){
        AmmeterUser user = getCuruser();
        ammeterCondition.setChannelId(user.getChannelId());
        ammeterCondition.setPageIndex(ammeterCondition.getPageIndex());
        ammeterCondition.setPageSize(ammeterCondition.getPageSize());
        return ammeterService.listAmmeterInfo(ammeterCondition);
    }
    @GetMapping(value = "/dailyReport/{imsi}/{reportDate}")
    @ApiOperation(value = "根据时间获取用电量报表",notes = "Legend")
    public Results dailyReport(@PathVariable("imsi") String imsi, @PathVariable("reportDate") String reportDate){
        Results result = null;
        List<Float> data = new ArrayList<>();
        AmmeterDevice device = ammeterService.selectDeviceByImsi(imsi);
        if(device == null){
            return Results.error("设备不存在");
        }
        if(reportDate.length() == 10) {
            if(DateUtils.stringToDate(reportDate) == null){
                 return  Results.error("时间有误!");
            }

            List<AmmeterReport> dailyReport = reportServer.listDailyReport(reportDate, device.getId());
            Map<String, Object> dailyMap = StringUtils.initDailyReportMap();
            if (dailyReport != null) {
                for (AmmeterReport report : dailyReport) {
                    dailyMap.put(String.valueOf(report.getHour()), report.getActiveEnergy());
                }
            }

            for (int i =1;i<=dailyMap.size();i++){
                try {
                    data.add(Float.parseFloat(String.valueOf(dailyMap.get(String.valueOf(i)))));
                }catch (Exception e){
                    data.add(0f);
                }
            }
            result = Results.success(data);
        }else if(reportDate.length() == 7){
            if(DateUtils.stringYearMonthToDate(reportDate) == null){
                return  Results.error("时间有误!");
            }
            Integer month = Integer.parseInt(reportDate.substring(reportDate.indexOf("-")+1));
            List<AmmeterMonthlyReport> monthlyReports = reportServer.listMonthReport(reportDate,device.getId());
            Map<String, Object> monthMap = StringUtils.initMonthReportMap(month);
            if(monthlyReports != null) {
                for (AmmeterMonthlyReport monthlyReport : monthlyReports) {
                    monthMap.put(String.valueOf(monthlyReport.getDay()),monthlyReport.getActiveEnergy());
                }
            }

            for (int i = 1; i <= monthMap.size();i++){
                try {
                    data.add(Float.parseFloat(String.valueOf(monthMap.get(String.valueOf(i)))));
                }catch (Exception e){
                    data.add(0f);
                }
            }
            result = Results.success(data);
        }
        return result == null ? Results.error("获取数据异常!"):result;
    }

    public static void main(String[] args) {
        String str ="2017-09";
        System.out.println(str.substring(0,str.indexOf("-")));
    }

}
