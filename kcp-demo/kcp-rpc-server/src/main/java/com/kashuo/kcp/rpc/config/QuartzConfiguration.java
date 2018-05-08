package com.kashuo.kcp.rpc.config;

import com.kashuo.kcp.rpc.schedule.IoMRegSync;
import com.kashuo.kcp.rpc.schedule.WarningInfoSync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 更新订购方案状态 定时任务
 * update by yuzhengwei on 2017/6/22.
 */
@Component
@Configuration
@EnableScheduling // 启用定时任务
@ComponentScan(basePackages = "com.kashuo.kcp.rpc.schedule")
public class QuartzConfiguration {

    @Value("${app.constant.quartz}")
    private String quartz;

    @Value("${app.constant.offSet}")
    private Boolean offSet;

    @Autowired
    private WarningInfoSync warningInfoSync;
    @Autowired
    private IoMRegSync regSync;

    /**
     * 定时更新订购方案状态，每天凌晨一点执行
     */
    @Scheduled(cron = "${app.constant.quartz}")
    public void reportCurrentByCron() throws Exception {
        if (offSet) {
            warningInfoSync.updateWarningInfoByCron();
        }
    }

    @Scheduled(cron = "${app.constant.quartz}")
    public void registerSync() throws Exception {
        if (offSet) {
            regSync.regInfo2IoM();
        }
    }

    @Scheduled(cron = "${app.constant.quartz}")
    public void sendAddressCommand() throws Exception {
        if (offSet) {
            regSync.sendAddressCommand();
        }
    }

    @Scheduled(cron = "${app.constant.quartz.hour.one}")
    public void send645PowerDeviceCommand() throws Exception {
        if (offSet) {
            regSync.send645PowerDeviceCommand();
        }
    }

    @Scheduled(cron = "${app.constant.quartz.hour.two}")
    public void send645VoltageDeviceCommand() throws Exception {
        if (offSet) {
            regSync.send645VoltageDeviceCommand();
        }
    }
    @Scheduled(cron = "${app.constant.quartz.hour.three}")
    public void send645CurrentDeviceCommand() throws Exception {
        if (offSet) {
            regSync.send645CurrentDeviceCommand();
        }
    }

}
