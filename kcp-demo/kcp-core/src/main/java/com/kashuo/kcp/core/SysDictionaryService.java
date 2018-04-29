/*
 * Copyright(c) 2017 kashuo.net All rights reserved.
 */
package com.kashuo.kcp.core;

import com.kashuo.kcp.constant.AppConstant;
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

	private static List<SysDictionary> callBackUrlsList = new ArrayList<>();

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
				systemParamsList = sysDictionaryMapper.querySystemParams(AppConstant.SYSTEM_PARAMS_TYPE_ID);
			}
			System.out.println("系统配置参数 加载成功 。。。。");
		} catch (Exception e) {
		}
		return systemParamsList;
	}

	@Autowired
	public List<SysDictionary> getCallBackUrlsList() {
		try {
			if (callBackUrlsList == null || callBackUrlsList.size() == 0) {
				callBackUrlsList = sysDictionaryMapper.querySystemParams(AppConstant.CALLBACK_URLS_TYPE_ID);
			}
			System.out.println("系统回调接口参数 加载成功 。。。。");
		} catch (Exception e) {
		}
		return callBackUrlsList;
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
	public static String getStaticSystemParamsValue(Integer paramId,String typeId) {
		String result;
		if(AppConstant.SYSTEM_PARAMS_TYPE_ID.equals(typeId)) {
			result = getParamsValuesByParamId(systemParamsList,paramId);
		}else{
            result = getParamsValuesByParamId(callBackUrlsList,paramId);
		}
		return result;
	}

	//获取静态的,数据只能通过重启服务器才能有,效率高
	public static String getStaticSystemParmasValue(String name,String typeId) {
		String result;
		if(AppConstant.SYSTEM_PARAMS_TYPE_ID.equals(typeId)) {
            result = getParamsValuesByName(systemParamsList,name);
		}else{
		    result = getParamsValuesByName(callBackUrlsList,name);
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
	public String getDynamicSystemValue(String name,String typeId) {
		String result = "";
		try {
			String value = getStaticSystemParmasValue(name,typeId);
			if (value != null) {
				return value;
			}

			if(AppConstant.SYSTEM_PARAMS_TYPE_ID.equals(typeId)) {
				if(systemParamsList == null){
					systemParamsList = sysDictionaryMapper.querySystemParams(typeId);
				}
				result = getParamsValuesByName(systemParamsList,name);
			}else{
				if(callBackUrlsList == null){
					callBackUrlsList = sysDictionaryMapper.querySystemParams(typeId);
				}
                result = getParamsValuesByName(callBackUrlsList,name);
			}
		} catch (Exception e) {

		}
		return result;
	}

	public void refreshSystemParams(){
		//刷新系统参数
        systemParamsList = sysDictionaryMapper.querySystemParams(AppConstant.SYSTEM_PARAMS_TYPE_ID);
        //刷新callback url参数
        callBackUrlsList = sysDictionaryMapper.querySystemParams(AppConstant.CALLBACK_URLS_TYPE_ID);
    }

    public void updateSystemParams(AmmeterSystemParams params){
	    sysDictionaryMapper.updateByName(AppConstant.IOM_APPID,params.getAppId());
        sysDictionaryMapper.updateByName(AppConstant.IOM_SECRET,params.getSecret());
		sysDictionaryMapper.updateByName(AppConstant.IOM_PLATFORM_IP,params.getPlatformIp());
		sysDictionaryMapper.updateByName(AppConstant.IOM_PLATFORM_IP,params.getPlatformPort());
        refreshSystemParams();
    }

	public List<SysDictionary> getDictionary(){
		return sysDictionaryMapper.selectByCondition();
	}

	public static String getParamsValuesByParamId(List<SysDictionary> list,Integer paramId){
		String result = "";
		for (SysDictionary dictionary : list) {
			if (paramId.intValue() == dictionary.getParamId()) {
				result = dictionary.getParam1();
			}
		}
		return result;
	}

	public static String getParamsValuesByName(List<SysDictionary> list,String name){
        String result = "";
        for (SysDictionary dictionary : list) {
            if (dictionary.getName().equals(name)) {
                result = dictionary.getParam1();
            }
        }
        return result;
    }

}
