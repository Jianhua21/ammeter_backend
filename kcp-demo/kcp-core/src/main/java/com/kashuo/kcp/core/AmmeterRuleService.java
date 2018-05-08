package com.kashuo.kcp.core;

import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.dao.AmmeterNetworkMapper;
import com.kashuo.kcp.dao.AmmeterRuleMapper;
import com.kashuo.kcp.dao.AmmeterWarningMapper;
import com.kashuo.kcp.domain.AmmeterNetwork;
import com.kashuo.kcp.domain.AmmeterRule;
import com.kashuo.kcp.domain.AmmeterWarning;
import com.kashuo.kcp.utils.CompareUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dell-pc on 2018/4/10.
 */
@Service
public class AmmeterRuleService {

    @Autowired
    private AmmeterRuleMapper ammeterRuleMapper;
    @Autowired
    private AmmeterNetworkMapper networkMapper;

    @Autowired
    private AmmeterWarningMapper warningMapper;
    private Logger logger = LoggerFactory.getLogger(AmmeterRuleService.class);
    private static List<AmmeterRule> netWorkRuleList = new ArrayList<>();

    @Autowired
    public List<AmmeterRule> getDictionartLists() {
        try {
            if (netWorkRuleList == null || netWorkRuleList.size() == 0) {
                netWorkRuleList = ammeterRuleMapper.getNetWorkRules();
            }
            System.out.println("Network报警规则 加载成功 。。。。"+netWorkRuleList.size());
        } catch (Exception e) {
        }
        return netWorkRuleList;
    }


    public void  generateNetWorkWarningInfo(AmmeterNetwork network){
        for (AmmeterRule rule:netWorkRuleList) {
            if (AppConstant.METER_RSRQ_KEY.equals(rule.getRuleParams().toUpperCase())) {
                insertWarningInfo(network, rule.getRuleDesc(),0);
            }
        }
    }

    public void offLineDeviceWarning(AmmeterNetwork network){
        insertWarningInfo(network,"当前设备不在线",1);
    }

    public void insertWarningInfo(AmmeterNetwork network,String desc,Integer type) {
        AmmeterWarning warning = new AmmeterWarning();
        warning.setCreateBy("system");
        warning.setAmmeterId(network.getAmmeterId());
        warning.setWarningDate(new Date());
        warning.setCreateDate(new Date());
        warning.setWarningDesc(desc);
        warning.setWarningStatus("0");
        warning.setWarningType(type);
        try {
            warningMapper.insert(warning);
        }catch (Exception e){
            logger.error("batch insert warning info failure...network.id={}",network.getId());
        }
}


    public boolean checkNetWorkWarning(AmmeterNetwork network,String name){
        boolean flag =false;
        for (AmmeterRule rule:netWorkRuleList){
            Field[] fields = network.getClass().getDeclaredFields();
            for (Field f:fields){
                if(f.getName().equals(rule.getRuleParams()) && f.getName().equals(name)){
                    flag = CompareUtils.compareParams(
                            String.valueOf(getFieldValueByName(f.getName(),network)),
                            rule.getRuleValue(),
                            rule.getRuleKey());
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) {
        AmmeterNetwork network = new AmmeterNetwork();
        network.setCelli("123");
        Field[] fields = network.getClass().getDeclaredFields();
        for (Field f:fields){
            System.out.println(f.getName());
        }

    }

    /**
     * 根据属性名获取属性值
     * */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            return null;
        }
    }
}
