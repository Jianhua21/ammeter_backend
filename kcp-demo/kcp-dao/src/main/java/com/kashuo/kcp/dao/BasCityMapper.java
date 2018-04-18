package com.kashuo.kcp.dao;


import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.domain.BasCity;

public interface BasCityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BasCity record);

    int insertSelective(BasCity record);

    BasCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasCity record);

    int updateByPrimaryKey(BasCity record);

    Page<?> selectByCondition();
}