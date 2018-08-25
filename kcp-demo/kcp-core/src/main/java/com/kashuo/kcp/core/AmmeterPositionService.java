package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterImeiMapper;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.dao.condition.AmmeterPositionCondition;
import com.kashuo.kcp.dao.result.PosotionHome;
import com.kashuo.kcp.domain.AmmeterImei;
import com.kashuo.kcp.domain.AmmeterPosition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dell-pc on 2018/3/20.
 */
@Service
public class AmmeterPositionService {

    private final static Logger logger = LoggerFactory.getLogger(AmmeterPositionService.class);

    @Autowired
    private AmmeterPositionMapper ammeterPositionMapper;

    @Autowired
    private AmmeterImeiMapper ammeterImeiMapper;

    public void insert(AmmeterPosition ammeterPosition){
        ammeterPositionMapper.insert(ammeterPosition);
    }

    public void updateByPrimaryKeySelective(AmmeterPosition ammeterPosition){
        ammeterPositionMapper.updateByPrimaryKeySelective(ammeterPosition);
    }
    public void updateAllByPrimaryKeySelective(AmmeterPosition ammeterPosition){
        ammeterPositionMapper.updateAllByPrimaryKeySelective(ammeterPosition);
    }

    public void deleteAmmeterPosition(Integer id){
        ammeterPositionMapper.deleteByPrimaryKey(id);
    }

    public AmmeterPosition getAmmeterPositionInfo(Integer id){
        return ammeterPositionMapper.selectByPrimaryKey(id);
    }

    public AmmeterPosition selectByImei(String imei){
        return ammeterPositionMapper.selectByImei(imei);
    }

    public AmmeterPosition selectByPrimaryKey(Integer id){
        return ammeterPositionMapper.selectByPrimaryKey(id);
    }

    public AmmeterPosition selectActiveByImei(String imei){
        return ammeterPositionMapper.selectActiveByImei(imei);
    }

    public List<AmmeterPosition>  selectPositionByStatus(Integer status){
        return ammeterPositionMapper.selectPositionByStatus(status);
    }

    public AmmeterPosition selectByDeviceId(String deviceId){
        return ammeterPositionMapper.selectByDeviceId(deviceId);
    }

    public Page<AmmeterPosition> getPositionList(AmmeterPositionCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return ammeterPositionMapper.getPositionList(condition);
    }

    public Page<PosotionHome> getGISList(AmmeterPositionCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return ammeterPositionMapper.getGISList(condition);
    }

    public AmmeterImei selectIMEIbyKey(String IMEI){
        return ammeterImeiMapper.selectByPrimaryKey(IMEI);
    }

}
