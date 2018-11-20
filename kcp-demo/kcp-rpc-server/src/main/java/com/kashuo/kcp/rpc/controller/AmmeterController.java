package com.kashuo.kcp.rpc.controller;

import com.kashuo.kcp.core.AmmeterChannelService;
import com.kashuo.kcp.core.AmmeterReportServer;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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



    @PostMapping(value = "/list")
    @ApiOperation(value="电表信息列表")
    public Results list(@RequestBody AmmeterCondition ammeterCondition ){
        AmmeterUser user = getCuruser();
        if(!isAdmin(user.getChannelId())) {
            ammeterCondition.setChannelId(user.getChannelId());
        }
        ammeterCondition.setPageIndex(ammeterCondition.getPageIndex());
        ammeterCondition.setPageSize(ammeterCondition.getPageSize());
        return ammeterService.listAmmeterInfo(ammeterCondition);
    }
    @GetMapping(value = "/dailyReport/{imsi}/{reportDate}")
    @ApiOperation(value = "根据时间获取用电量报表",notes = "Legend")
    public Results dailyReport(@PathVariable("imsi") String imsi, @PathVariable("reportDate") String reportDate){

        return dailyOrMonthlyReport(imsi,reportDate,1);
    }

    public Results dailyOrMonthlyReport(String imsi,String reportDate,Integer reportType){
        Results result = null;
        AmmeterDevice device = ammeterService.selectDeviceByImsi(imsi);
        if(device == null){
            return Results.error("设备不存在");
        }
        if(reportDate.length() == 10) {
            if(DateUtils.stringToDate(reportDate) == null){
                return  Results.error("时间有误!");
            }

            List<Float> data  = reportServer.list645DailyReport(reportDate,device.getId(),reportType);
            result = Results.success(data);
        }else if(reportDate.length() == 7){
            if(DateUtils.stringYearMonthToDate(reportDate) == null){
                return  Results.error("时间有误!");
            }
            if(reportType ==1) {
                List<Float> data = reportServer.list645MonthReport(reportDate, device.getId(), reportType);
                result = Results.success(data);
            }else {
                result =Results.error("该数据没有月报表!");
            }
        }
        return result == null ? Results.error("获取数据异常!"):result;
    }
    @GetMapping(value = "/powerReport/{imsi}/{reportDate}")
    @ApiOperation(value = "根据时间获取用电量报表,根据时间格式获取月报表(2018-05) 和 日报表(2018-05-05)",notes = "Legend")
    public Results powerReport(@PathVariable("imsi") String imsi, @PathVariable("reportDate") String reportDate){
        return dailyOrMonthlyReport(imsi,reportDate,1);
    }
    @GetMapping(value = "/voltageReport/{imsi}/{reportDate}")
    @ApiOperation(value = "根据时间获取电压报表,根据时间格式获取日报表(2018-05-05)",notes = "Legend")
    public Results voltageReport(@PathVariable("imsi") String imsi, @PathVariable("reportDate") String reportDate){
        return dailyOrMonthlyReport(imsi,reportDate,2);
    }
    @GetMapping(value = "/currentReport/{imsi}/{reportDate}")
    @ApiOperation(value = "根据时间获取电流报表,根据时间格式获取日报表(2018-05-05)",notes = "Legend")
    public Results currentReport(@PathVariable("imsi") String imsi, @PathVariable("reportDate") String reportDate){
        return dailyOrMonthlyReport(imsi,reportDate,3);
    }

    public static void main(String[] args) {
        String str ="2017-09";
        System.out.println(str.substring(0,str.indexOf("-")));
    }

}
