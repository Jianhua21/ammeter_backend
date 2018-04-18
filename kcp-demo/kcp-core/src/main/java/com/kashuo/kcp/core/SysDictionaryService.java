/*
 * Copyright(c) 2017 kashuo.net All rights reserved.
 */
package com.kashuo.kcp.core;

import com.kashuo.kcp.dao.SysDictionaryMapper;
import com.kashuo.kcp.dao.condition.AmmeterSystemParams;
import com.kashuo.kcp.domain.SysDictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The base service for SysDictionary
 * @author dell
 * @Time 2017-04-12 16:24:53
 */
@Service
public class SysDictionaryService {
	
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;

	private static List<SysDictionary> dictionaryList = new ArrayList<>();

	private static List<SysDictionary> systemParamsList = new ArrayList<>();

	@Autowired
	public List<SysDictionary> getDictionartLists() {
		try {
			if (dictionaryList == null || dictionaryList.size() == 0) {
				dictionaryList = sysDictionaryMapper.selectByCondition();
			}
			System.out.println("数据字典 加载成功 。。。。");
		} catch (Exception e) {
		}
		return dictionaryList;
	}

	@Autowired
	public List<SysDictionary> getSystemParamsList() {
		try {
			if (systemParamsList == null || systemParamsList.size() == 0) {
				systemParamsList = sysDictionaryMapper.querySystemParams();
			}
			System.out.println("系统配置参数 加载成功 。。。。");
		} catch (Exception e) {
		}
		return systemParamsList;
	}

	public SysDictionary getDictionaryByIndex(Integer index){
		return dictionaryList.get(index-1);
	}
	//获取静态的,数据只能通过重启服务器才能有,效率高
	public static String getStaticValue(Integer paramId) {
		String result = null;
		for (SysDictionary dictionary : dictionaryList) {
			if (paramId.intValue() == dictionary.getParamId()) {
				result =dictionary.getParam1()+
						dictionary.getParam2()+
						dictionary.getParam3()+
						dictionary.getParam4();
				return result;
			}
		}
		return result;
	}

	//获取静态的,数据只能通过重启服务器才能有,效率高
	public static String getStaticSystemParmasValue(String name) {
		String result = null;
		for (SysDictionary dictionary : systemParamsList) {
			if (dictionary.getName().equals(name)) {
				result = dictionary.getParam1();
			}
		}
		return result;
	}
	//先从静态中获取,如果没有再查询一遍
	public String getDynamicValue(Integer paramId,String ammeterNumber) {
		String result = "";
		try {
			String value = getStaticValue(paramId);
			if (value != null) {
				return value;
			}
			if(dictionaryList == null){
				dictionaryList = sysDictionaryMapper.selectByCondition();
			}
			for (SysDictionary dictionary : dictionaryList) {
				if (paramId.intValue() == dictionary.getParamId()) {
					result =dictionary.getParam1()+
							dictionary.getParam2()+
							dictionary.getParam3()+
							dictionary.getParam4();
					return result;
				}
			}
		} catch (Exception e) {

		}
		return result;
	}
	//先从静态中获取,如果没有再查询一遍
	public String getDynamicValue(Integer paramId) {
		String result = "";
		try {
			String value = getStaticValue(paramId);
			if (value != null) {
				return value;
			}
			if(dictionaryList == null){
				dictionaryList = sysDictionaryMapper.selectByCondition();
			}
			System.out.println("=======================================");
			for (SysDictionary dictionary : dictionaryList) {
				if (paramId.intValue() == dictionary.getParamId()) {
					result =dictionary.getParam1()+
							dictionary.getParam2()+
							dictionary.getParam3()+
							dictionary.getParam4();
					return result;
				}
			}
		} catch (Exception e) {

		}
		return result;
	}


	//先从静态中获取,如果没有再查询一遍
	public String getDynamicSystemValue(String name) {
		String result = "";
		try {
			String value = getStaticSystemParmasValue(name);
			if (value != null) {
				return value;
			}
			if(systemParamsList == null){
				systemParamsList = sysDictionaryMapper.querySystemParams();
			}
			for (SysDictionary dictionary : systemParamsList) {
				if (dictionary.getName().equals(name)) {
					result =dictionary.getParam1();
					return result;
				}
			}
		} catch (Exception e) {

		}
		return result;
	}

	public void refreshSystemParams(){
        systemParamsList = sysDictionaryMapper.querySystemParams();
    }

    public void updateSystemParams(AmmeterSystemParams params){
	    sysDictionaryMapper.updateByName("appId",params.getAppId());
        sysDictionaryMapper.updateByName("secret",params.getSecret());
		sysDictionaryMapper.updateByName("platformIp",params.getPlatformIp());
		sysDictionaryMapper.updateByName("platformPort",params.getPlatformPort());
        refreshSystemParams();
    }

	public List<SysDictionary> getDictionary(){
		return sysDictionaryMapper.selectByCondition();
	}
}
