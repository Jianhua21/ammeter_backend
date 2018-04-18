package com.kashuo.kcp.rpc.config;

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

    /**
     * 定时更新订购方案状态，每天凌晨一点执行
     */
    @Scheduled(cron = "${app.constant.quartz}")
    public void reportCurrentByCron() throws Exception {
        if (offSet) {
            warningInfoSync.updateWarningInfoByCron();
        }
    }
}
