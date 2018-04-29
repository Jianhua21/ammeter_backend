package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.SysDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDictionaryMapper {

    List<SysDictionary> selectByCondition();

    List<SysDictionary> querySystemParams(String typeId);

    int updateByName(@Param("name")String name,@Param("value") String value);
}