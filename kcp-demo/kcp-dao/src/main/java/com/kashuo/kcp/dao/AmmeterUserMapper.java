package com.kashuo.kcp.dao;

import com.kashuo.kcp.dao.condition.AmmeterUserCondition;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.common.base.domain.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AmmeterUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterUser record);

    int insertSelective(AmmeterUser record);

    AmmeterUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AmmeterUser record);

    int updateByPrimaryKey(AmmeterUser record);

    AmmeterUser login(String username);

    AmmeterUser selectUserByMobilePhone(String mobilePhone);

    List<String> loadPriCode(Integer roleid);

    List<String> removePriCode(@Param("menuUrlList") List<String> menuUrlList);

    AmmeterUser checkToken(String token);

    Page<?> selectUserMangerList(AmmeterUserCondition condition);

    AmmeterUser checkUserName(String loginName);

    AmmeterUser checkOtherUserName(@Param("loginName") String loginName, @Param("id") Integer id);

    AmmeterUser checkMobile(String mobilePhone);

    AmmeterUser checkOtherMobile(@Param("mobilePhone") String mobilePhone, @Param("id") Integer id);

    Page<?> selectListSelective(AmmeterUserCondition condition);

    List<AmmeterUser> selectUserByChannelList(@Param("channelIdList") List<Integer> channelIdList, @Param("userId") Integer userId);
}