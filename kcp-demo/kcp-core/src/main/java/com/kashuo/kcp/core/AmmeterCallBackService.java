package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterCallbackHistoryMapper;
import com.kashuo.kcp.dao.condition.CallBackCondition;
import com.kashuo.kcp.domain.AmmeterCallbackHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/23.
 */
@Service
public class AmmeterCallBackService {

    @Autowired
    private AmmeterCallbackHistoryMapper callbackHistoryMapper;

    public Page<AmmeterCallbackHistory> getCallBackHistoryByDeviceId(CallBackCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return callbackHistoryMapper.getCallBackHistoryByDeviceId(condition.getDeviceId());
    }

    public void insertCallBackHistory(AmmeterCallbackHistory callbackHistory){
        if(callbackHistory.getDeviceId() == null){
            callbackHistory.setDeviceId("-");
        }
        if(callbackHistory.getNotifyType() == null){
            callbackHistory.setNotifyType("-");
        }
        callbackHistoryMapper.insert(callbackHistory);
    }
}
