package com.kashuo.kcp.rpc.thread;

import com.alibaba.fastjson.JSON;
import com.kashuo.kcp.core.ProcessMessageService;
import com.kashuo.kcp.utils.NbiotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mr.Legend on 2019/7/1.
 * →→→→如果有问题请联系→→→→
 * penghuazi@126.com
 * →→→→→→→→→→→→→→→→
 */
public class ProcessMessageThread extends Thread  {

    private static Logger logger = LoggerFactory.getLogger(ProcessMessageThread.class);

    private ProcessMessageService processMessageService;

    private NbiotUtils.BodyObj obj ;
    public ProcessMessageThread(ProcessMessageService processMessageService,NbiotUtils.BodyObj obj){
        this.processMessageService =processMessageService;
        this.obj =obj;
    }

    @Override
    public void run() {
        logger.info("正在处理上报的数据处理中，:{}", JSON.toJSONString(obj));
        processMessageService.processMessage(obj);
        logger.info("处理完成!");
    }

}
