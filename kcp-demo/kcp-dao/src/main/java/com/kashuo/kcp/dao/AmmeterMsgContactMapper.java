package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterMsgContact;
import org.apache.ibatis.annotations.Param;

public interface AmmeterMsgContactMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterMsgContact record);

    int insertSelective(AmmeterMsgContact record);

    AmmeterMsgContact selectByPrimaryKey(Integer id);

    AmmeterMsgContact getMsgInfoByCondition(@Param("channelId") Integer channelId,@Param("projectId") Integer projectId);

    int updateByPrimaryKeySelective(AmmeterMsgContact record);

    int updateByPrimaryKey(AmmeterMsgContact record);
}
