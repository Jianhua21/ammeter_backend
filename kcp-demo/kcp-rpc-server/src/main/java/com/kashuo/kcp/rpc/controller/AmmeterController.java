package com.kashuo.kcp.rpc.controller;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterChannelService;
import com.kashuo.kcp.core.AmmeterReportServer;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.condition.AmmeterCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterInfoResult;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.ExcelHelper;
import com.kashuo.kcp.utils.ReflectHelper;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
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



    @PostMapping(value = "/list")
    @ApiOperation(value="电表信息列表")
    public Results<Page<AmmeterInfoResult>> list(@RequestBody AmmeterCondition ammeterCondition ){
        AmmeterUser user = getCuruser();
        ammeterCondition.setChannelId(user.getChannelId());
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

    @RequestMapping(value = "export", method = RequestMethod.GET)
    @ApiOperation(value = "列表导出", notes = "Created by Mr.ZHAO on 2018/10/21")
    public void export(AmmeterCondition ammeterCondition, HttpServletRequest request, HttpServletResponse response) throws
            ParseException {
        ammeterCondition.setPageSize(10000000);
        Page<AmmeterInfoResult> reports = list(ammeterCondition).getData();

        String[] mappingFileds = new String[]{"id", "name", "address", "remark", "onlineStatus", "activePower",
                "companyName", "agreementStatus", "contactInfo"};
        String[] titles = new String[]{"设备编号", "设备名称", "电表位置", "备注", "在线状态", "电表即时度数", "用电单位", "合同状态",
                "联系人信息"};

        // 将 List<Report> 转化为 List<Map<String, Object>> 格式
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (AmmeterInfoResult report : reports) {
            Map<String, Object> objectMap = ReflectHelper.fetchFields(report);
            mapList.add(objectMap);
        }
        HSSFWorkbook excel = ExcelHelper.createExcel(mapList, titles, mappingFileds, null, false);
        ExcelHelper.export(request, response, excel, "电表电量-" + DateUtils.getCurrentDate("yyyyMMdd"));
    }
    public static void main(String[] args) {
        String str ="2017-09";
        System.out.println(str.substring(0,str.indexOf("-")));
    }

}
