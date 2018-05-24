package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterWorkingInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmmeterWorkingInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterWorkingInfo record);

    int insertSelective(AmmeterWorkingInfo record);

    AmmeterWorkingInfo selectByPrimaryKey(Integer id);

    AmmeterWorkingInfo selectByAmmeterId(Integer ammeterId);

    int updateByAmmeterIdSelective(AmmeterWorkingInfo record);

    int updateByAmmeterId(@Param("ammeterId") Integer ammeterId, @Param("status") Integer status);

    List<AmmeterWorkingInfo>  getWrongStatusforUpdate();

    int updateByPrimaryKey(AmmeterWorkingInfo record);
}