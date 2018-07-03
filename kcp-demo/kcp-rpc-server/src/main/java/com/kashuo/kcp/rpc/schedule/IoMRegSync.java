package com.kashuo.kcp.rpc.schedule;

import com.huawei.iotplatform.client.NorthApiException;
import com.kashuo.kcp.auth.AuthExceptionService;
import com.kashuo.kcp.command.CommandService;
import com.kashuo.kcp.core.AmmeterPositionService;
import com.kashuo.kcp.core.AmmeterReportServer;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.dao.result.AmmeterDeviceResult;
import com.kashuo.kcp.domain.AmmeterMonthlyReport;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.domain.AmmeterReport;
import com.kashuo.kcp.domain.AmmeterWorkingInfo;
import com.kashuo.kcp.utils.DateUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.httpclient.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dell-pc on 2018/4/28.
 */

@Component
public class IoMRegSync {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AmmeterPositionService ammeterPositionService;
    @Autowired
    private CommandService commandService;
    @Autowired
    private AmmeterService ammeterService;

    @Autowired
    private AuthExceptionService authExceptionService;

    @Autowired
    private AmmeterReportServer reportServer;

    public void regInfo2IoM(){

        //对电表状态容错定时恢复
        List<AmmeterWorkingInfo> workingInfos = reportServer.getWrongWorkInfo4Update();
        if(workingInfos != null){
            workingInfos.forEach(w->{
                Integer status = 1;
                if(w.getStatus() ==3){
                    status = 2;
                }
                reportServer.updateByAmmeterId(w.getAmmeterId(),status);
            });
        }

        //获取注册失败的设备
        List<AmmeterPosition> positions_2 = ammeterPositionService.selectPositionByStatus(2);
        positions_2.forEach(p->{
            try {
                Integer result = commandService.autoRegDevice(p);
                AmmeterPosition position = new AmmeterPosition();
                position.setStatus(result);
                position.setId(p.getId());
                ammeterPositionService.updateByPrimaryKeySelective(position);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,null);
            }
        });
        //获取信息同步失败的设备
        List<AmmeterPosition> positions_4 = ammeterPositionService.selectPositionByStatus(4);
        positions_4.forEach(p->{
            try {
                commandService.autoSyncDeviceInfo(p);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,p.getDeviceId());
            }
        });

    }

    public void sendAddressCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.checkAmmeterMeterNo();
//        results.stream().collect(Collectors.partitioningBy(r->"1".equals(r.getImei())));
        results.forEach(r->{
            try {
                commandService.getAmmeterAddress(r.getDeviceId());
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("下发电表地址出错,deviceId: {}",r.getDeviceId());
            }catch (Exception e) {
                logger.error("下发电表地址出错,deviceId: {}",r.getDeviceId());
            }
        });
    }

    public void send645PowerDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645PowerDeviceCommand(r);
            } catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("下发用电量出错,deviceId: {}",r.getDeviceId());
             } catch (Exception e) {
                logger.error("发送645 电量命令出错!, {}",r.getDeviceId());
            }
        });
    }
    public void send645VoltageDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645VoltageDeviceCommand(r);
            }catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("发送645 电压命令出错!,deviceId: {}",r.getDeviceId());
            } catch (Exception e) {
                logger.error("发送645 电压命令出错!, {}",r.getDeviceId());
            }
        });
    }
    public void send645CurrentDeviceCommand(){
        List<AmmeterDeviceResult>  results = ammeterService.validAmmeterDevice();
        results.forEach(r->{
            try {
                commandService.send645CurrentDeviceCommand(r);
            }catch (NorthApiException e) {
                authExceptionService.handleException(e,r.getDeviceId());
                logger.error("发送645 电流命令出错!,deviceId: {}",r.getDeviceId());
            }  catch (Exception e) {
                logger.error("发送645 电流命令出错!, {}",r.getDeviceId());
            }
        });
    }

    public void updateMonthPowerReport(){

        List<AmmeterReport> reports = reportServer.getMonthlyPowers();
        List<AmmeterMonthlyReport> monthlyReports = new ArrayList<>();
        reports.forEach(r->{
            AmmeterMonthlyReport report = new AmmeterMonthlyReport();
            report.setMonth(String.valueOf(DateUtils.getMonth()-1));
            report.setActiveEnergy(r.getActiveEnergy());
            report.setAmmeterId(r.getAmmeterId());
            report.setType(1);
            report.setDay(DateUtils.getYear());
            report.setSendDate(new Date());
            monthlyReports.add(report);
        });
        if(monthlyReports.size() > 0) {
            reportServer.batchInsertPowerMonthlyReport(monthlyReports);
        }

    }
    public static void main(String[] args) {

        int i = 3;
        double j = 7.2;
        Map<String,Object> order = new HashedMap();
        order.put("price",7076000);
        BigDecimal bg = new BigDecimal((Integer) order.get("price")/100*25/30*8/1000).setScale(0, RoundingMode.HALF_UP);
//        BigDecimal bg = new BigDecimal(j*60/100).setScale(0, RoundingMode.HALF_UP);
        String s = String.format("%08d",(int)bg.doubleValue()*1000);
        System.out.println(s);
//        System.out.println((int)order.get("price")*(25*18/30/100d));

//        System.out.println(Integer.parseInt(s)*1d/1000);
    }

}
