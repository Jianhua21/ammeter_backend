package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterNetworkMapper;
import com.kashuo.kcp.dao.AmmeterWarningMapper;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.domain.AmmeterNetwork;
import com.kashuo.kcp.domain.AmmeterWarningResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell-pc on 2018/4/9.
 */
@Service
public class AmmeterWarningService {

    @Autowired
    private AmmeterNetworkMapper networkMapper;
    @Autowired
    private AmmeterRuleService ruleService;
    @Autowired
    private AmmeterWarningMapper warningMapper;

    public void updateWarningInfo(){
        List<AmmeterNetwork> networks = networkMapper.selectForWarningReport();
        for (AmmeterNetwork network:networks){
            ruleService.generateNetWorkWarningInfo(network);
        }
         System.out.println("测试定时器");

    }

    public Page<AmmeterWarningResult> queryWarningList(WarningCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return warningMapper.queryWarningList(condition);
    }
}
