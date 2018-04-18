package com.kashuo.kcp.core.test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by dell-pc on 2017/11/15.
 */
public class CacheHealthChecker  extends BaseHealthChecker{

    public CacheHealthChecker (CountDownLatch latch)  {
        super("Cache Service", latch);
    }

    @Override
    public void verifyService()
    {
        System.out.println("Checking " + this.getServiceName());
        try
        {
            Thread.sleep(8000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println(this.getServiceName() + " is UP");
    }
}
