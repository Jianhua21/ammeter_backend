package com.kashuo.kcp.core.test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dell-pc on 2017/11/15.
 */
public class DatabaseHealthChecker extends BaseHealthChecker{

    public DatabaseHealthChecker (CountDownLatch latch)  {
        super("Database Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(6000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
