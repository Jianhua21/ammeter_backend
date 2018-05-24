package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.AmmeterMonthlyReportMapper;
import com.kashuo.kcp.dao.AmmeterReportMapper;
import com.kashuo.kcp.dao.AmmeterWorkingInfoMapper;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterMonthlyReport;
import com.kashuo.kcp.domain.AmmeterReport;
import com.kashuo.kcp.domain.AmmeterWorkingInfo;
import com.kashuo.kcp.utils.AmmeterUtils;
import com.kashuo.kcp.utils.DateUtils;
import com.kashuo.kcp.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by dell-pc on 2017/9/16.
 */
@Service
public class AmmeterReportServer {
    private Logger logger = LoggerFactory.getLogger(AmmeterReportServer.class);

    @Autowired
    private AmmeterMonthlyReportMapper monthlyReportMapper;

    @Autowired
    private AmmeterReportMapper reportMapper;

    @Autowired
    private SysDictionaryService sysDictionaryService;

    @Autowired
    private AmmeterWorkingInfoMapper workingInfoMapper;

    public int insertDailyReportServer(AmmeterDevice device, String result){
        AmmeterReport reportDB = reportMapper.queryMaxDailyReportByAmmeterId(device.getId(),DateUtils.getCurrentDate());
        AmmeterReport report = new AmmeterReport();
        report.setAmmeterId(device.getId());
        report.setDateTime(DateUtils.getCurrentDate());
        Float f = 0f;
        try{
            if(reportDB != null) {
                f = Float.parseFloat(result) - Float.parseFloat(reportDB.getActiveEnergy());
            }else{
                f = Float.parseFloat(result);
            }
        }catch (Exception e){
            logger.error("parse Float error",e);
        }
        report.setActiveEnergy(String.valueOf(f));
        if(DateUtils.getHour() ==0){
            report.setHour(24);
        }else{
            report.setHour(DateUtils.getHour());
        }

        report.setSendTime(new Date());
       return  reportMapper.insert(report);
    }


    public int processDailyReportServer(AmmeterDevice device, String response,Integer type) throws Exception {
        //解包645命令
        String result = AmmeterUtils.unPackageAnalysis(response);

        logger.info("处理回调的数据，数据类型:{},解包前:{},解包后:{}",type,response,result);
        AmmeterReport reportDB = reportMapper.queryMaxDailyReportByCondition(device.getId(),
                DateUtils.getCurrentDate(),DateUtils.getHour()+1);
        AmmeterReport report = new AmmeterReport();
        report.setAmmeterId(device.getId());
        report.setDateTime(DateUtils.getCurrentDate());
        Float f = 0f;
        boolean updateFlag = false;
        try{
            if(reportDB != null) {
                updateFlag = true;
            }
            f = Float.parseFloat(result);
        }catch (Exception e){
            logger.error("parse Float error ",e);
        }
        if(type ==1){
            report.setActiveEnergy(String.valueOf(f));
        }else if(type ==2){
            report.setVoltage(String.valueOf(f));
        }else if(type ==3){
            report.setCurrent(String.valueOf(f));
        }

        Integer results  =0 ;
        if(updateFlag) {
            report.setId(reportDB.getId());
            results = reportMapper.updateByPrimaryKeySelective(report);
        }else{
            report.setHour(DateUtils.getHour()+1);
            report.setType(3);
            report.setSendTime(new Date());
            results = reportMapper.insertSelective(report);
        }
        return results;
    }



    public String sendReponseParamsForReport(AmmeterReport report){
        String reponseStr ="-1";
        if("".equals(sysDictionaryService.getDynamicValue(11))){
            return reponseStr;
        }
        if(DateUtils.getHour() == 0){
            report.setDateTime(DateUtils.getLastDayDate());
            report.setHour(24);
        }else{
            System.out.println("===================");
            report.setDateTime(DateUtils.getCurrentDate());
            report.setHour(DateUtils.getHour());

        }
        AmmeterReport reportDB = reportMapper.queryByParams(report);
        if(reportDB == null){
            //当前正向总功率
            reponseStr =sysDictionaryService.getDynamicValue(11);
        }
        return reponseStr;
    }

    public String sendReponseParamsForReport(AmmeterReport report,String ammeterNumber){
        String reponseStr ="-1";
        if("".equals(sysDictionaryService.getDynamicValue(11,ammeterNumber))){
            return reponseStr;
        }
        if(DateUtils.getHour() == 0){
            report.setDateTime(DateUtils.getLastDayDate());
            report.setHour(24);
        }else{
            report.setDateTime(DateUtils.getCurrentDate());
            report.setHour(DateUtils.getHour());
        }
        AmmeterReport reportDB = reportMapper.queryByParams(report);
        if(reportDB == null){
               //当前正向总功率
                reponseStr =sysDictionaryService.getDynamicValue(11,ammeterNumber);
        }
        return reponseStr;
    }

    public List<AmmeterReport> listDailyReport(String reportDate,Integer ammeterId){
        AmmeterReport report = new AmmeterReport();
        report.setDateTime(reportDate);
        report.setAmmeterId(ammeterId);
        return  reportMapper.dailyReportByParams(report);
    }

    public List<Float> list645DailyReport(String reportDate,Integer ammeterId,Integer reportType){
        List<Float> data = new ArrayList<>();
        List<AmmeterReport> dailyReport = listDailyReport(reportDate, ammeterId);
        Map<String, Object> dailyMap = StringUtils.initDailyReportMap();
        if (dailyReport != null) {
            for (AmmeterReport report : dailyReport) {
                if(reportType == 1) {
                    //获取电量
                    dailyMap.put(String.valueOf(report.getHour()), report.getActiveEnergy());
                }else if(reportType == 2){
                    //获取电压
                    dailyMap.put(String.valueOf(report.getHour()), report.getVoltage());
                }else{
                    //获取电流
                    dailyMap.put(String.valueOf(report.getHour()), report.getCurrent());
                }
            }
        }

        for (int i =1;i<=dailyMap.size();i++){
            Float result = 0f;
            try {
                if(i>1 && dailyMap.get(String.valueOf(i)) == null){
                    if(DateUtils.dateToString(new Date()).equals(reportDate) ) {
                        if(i < DateUtils.getHour()) {
                            dailyMap.put(String.valueOf(i), dailyMap.get(String.valueOf(i - 1)));
                            result = Float.parseFloat(String.valueOf(dailyMap.get(String.valueOf(1))));
                        }
                    }else {
                        dailyMap.put(String.valueOf(i), dailyMap.get(String.valueOf(i - 1)));
                        result =  Float.parseFloat(String.valueOf(dailyMap.get(String.valueOf(1))));
                    }

                }else {
                    result = Float.parseFloat(String.valueOf(dailyMap.get(String.valueOf(i))));
                }
                data.add(result);
            }catch (Exception e){
                data.add(0f);
            }
        }
        return data;
    }

    public List<AmmeterMonthlyReport> listMonthReport(String time, Integer ammeterId){
        AmmeterMonthlyReport monthlyReport = new AmmeterMonthlyReport();
        monthlyReport.setAmmeterId(ammeterId);
        monthlyReport.setMonth(time);
        return monthlyReportMapper.queryByParams(monthlyReport);
    }

    public void updatePowerMonthlyReport(AmmeterMonthlyReport monthlyReport){
        monthlyReportMapper.updateByPrimaryKeySelective(monthlyReport);
    }

    public void batchInsertPowerMonthlyReport(List<AmmeterMonthlyReport> reports){
            monthlyReportMapper.batchInsert(reports);
    }

    public List<AmmeterReport>  getMonthlyPowers(){
        return reportMapper.getMonthlyPowers();
    }




    public List<Float>  list645MonthReport(String reportDate,Integer ammeterId,Integer reportType){
        List<Float> data = new ArrayList<>();
        Integer month = Integer.parseInt(reportDate.substring(reportDate.indexOf("-")+1));
        List<AmmeterMonthlyReport> monthlyReports = listMonthReport(reportDate,ammeterId);
        Map<String, Object> monthMap = StringUtils.initMonthReportMap(month);
        if(monthlyReports != null) {
            for (AmmeterMonthlyReport monthlyReport : monthlyReports) {
                if(reportType ==1) {
                    monthMap.put(String.valueOf(monthlyReport.getDay()), monthlyReport.getActiveEnergy());
                }
            }
        }

        for (int i = 1; i <= monthMap.size();i++){
            try {
                data.add(Float.parseFloat(String.valueOf(monthMap.get(String.valueOf(i-1)))));
            }catch (Exception e){
                data.add(0f);
            }
        }
        return data;
    }

    public List<AmmeterWorkingInfo> getWrongWorkInfo4Update(){
        return workingInfoMapper.getWrongStatusforUpdate();
    }

    public Integer updateByAmmeterId(Integer ammeterId,Integer status){
        return workingInfoMapper.updateByAmmeterId(ammeterId,status);
    }

}
