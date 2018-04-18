package com.kashuo.kcp.rpc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by feilaoda on 2017/8/14.
 */
@SpringBootApplication(scanBasePackages = {"com.kashuo.kcp"})
@EnableTransactionManagement
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
//        ApplicationContext ctx = new SpringApplicationBuilder(App.class).web(true).run(args);
//        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
//        CouponEngineManager manager = ctx.getBean("couponEngineManager", CouponEngineManager.class);
    }


}
