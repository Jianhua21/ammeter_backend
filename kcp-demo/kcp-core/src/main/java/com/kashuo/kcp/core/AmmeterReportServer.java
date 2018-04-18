package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.AmmeterMonthlyReportMapper;
import com.kashuo.kcp.dao.AmmeterReportMapper;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterMonthlyReport;
import com.kashuo.kcp.domain.AmmeterReport;
import com.kashuo.kcp.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    public List<AmmeterMonthlyReport> listMonthReport(String time, Integer ammeterId){
        AmmeterMonthlyReport monthlyReport = new AmmeterMonthlyReport();
        monthlyReport.setAmmeterId(ammeterId);
        monthlyReport.setMonth(time);
        return monthlyReportMapper.queryByParams(monthlyReport);
    }
}
