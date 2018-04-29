package com.kashuo.kcp.core;

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.dao.AmmeterLoginHistoryMapper;
import com.kashuo.kcp.domain.AmmeterLoginHistory;
import com.kashuo.kcp.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by dell-pc on 2018/4/26.
 */
@Service
public class AmmeterLoginHistoryService {

    private static String AMAP_URL="https://restapi.amap.com/v3/ip?ip=";

    public static final String AMAP_PROVINCE ="province";

    public static final String AMAP_CITY ="city";

    public static final String AMAP_RECTANGLE ="rectangle";


    @Autowired
    private AmmeterLoginHistoryMapper loginHistoryMapper;


    public Integer insertLoginHistory(AmmeterLoginHistory loginHistory,String key) throws IOException {

        JSONObject json = getUserRemoteAddByIp(loginHistory.getRemoteIp(),key);
        loginHistory.setLoginProvince(json.getString(AMAP_PROVINCE));
        loginHistory.setLoginAddress(json.getString(AMAP_CITY));
        loginHistory.setAmapAdd(json.getString(AMAP_RECTANGLE));
        return loginHistoryMapper.insert(loginHistory);
    }

    public JSONObject getUserRemoteAddByIp(String IP,String key) throws IOException {

        StringBuilder sb = new StringBuilder(AMAP_URL);
        sb.append(IP).append("&output=JSON&key=").append(key);
        return HttpClientUtils.getDataFromGetMethod(sb.toString());

    }

    public static void main(String[] args) throws IOException {
        AmmeterLoginHistoryService service = new AmmeterLoginHistoryService();
        service.getUserRemoteAddByIp("112.25.154.148","7af099c49bc59b423c4998a8bc01706b");
    }

}
