package com.kashuo.kcp.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysLogErrorMapper {

    int insert(@Param("map") Map<String, Object> map);
}