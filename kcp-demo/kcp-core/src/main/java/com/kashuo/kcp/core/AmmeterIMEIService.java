package com.kashuo.kcp.core;

import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterImeiMapper;
import com.kashuo.kcp.dao.result.AmmeterIMEIResult;
import com.kashuo.kcp.domain.AmmeterImei;
import com.kashuo.kcp.utils.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell-pc on 2018/8/11.
 */
@Service
public class AmmeterIMEIService {

    @Autowired
    private AmmeterImeiMapper ammeterImeiMapper;

    public Results  saveImei(AmmeterImei imei){
        AmmeterImei imeiDB =  ammeterImeiMapper.selectByPrimaryKey(imei.getImei());
        if(imeiDB != null){
            return Results.error("IMEI已存在!");
        }else{
            ammeterImeiMapper.insert(imei);
        }
        return Results.success("IMEI录入成功!");

    }

    public List<AmmeterIMEIResult> listPages(AmmeterImei condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return ammeterImeiMapper.queryImeiByCondition(condition);
    }

    public Results deleteImei(String imei){
        AmmeterImei condition = new AmmeterImei();
        condition.setImei(imei);
        List<AmmeterIMEIResult> results  =ammeterImeiMapper.queryImeiByCondition(condition);
        if(results != null && results.size() >0){
            if(results.get(0).getDeviceName() != null) {
                return Results.error("IMEI 已被使用!");
            }
        }
        ammeterImeiMapper.deleteByPrimaryKey(imei);
        return Results.success("IMEI已删除!");
    }


}
