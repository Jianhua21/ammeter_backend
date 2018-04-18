package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterNetworkMapper;
import com.kashuo.kcp.dao.condition.AmmeterHandleResult;
import com.kashuo.kcp.dao.condition.AmmeterNetWorkCondition;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterNetWorkResult;
import com.kashuo.kcp.domain.AmmeterNetwork;
import com.kashuo.kcp.domain.SysDictionary;
import com.kashuo.kcp.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by dell-pc on 2017/9/22.
 */
@Service
public class NetWorkService {

    @Autowired
    private AmmeterNetworkMapper networkMapper;
    @Autowired
    private SysDictionaryService dictionaryService;


    public String getNetWorkResponseByAmmeterId(Integer ammeterId,String ammeterNumber){
        String responseStr ="-1";
        AmmeterNetwork network = networkMapper.selectByAmmeterId(ammeterId);
        if("".equals(dictionaryService.getDynamicValue(32))){
            return responseStr;
        }
        if(network != null){

            if(new Date().getTime() - network.getCreatedTime().getTime() > 30*60*1000l )  {
                responseStr =  dictionaryService.getDynamicValue(32,ammeterNumber);
            }

        }else {
            responseStr =  dictionaryService.getDynamicValue(32,ammeterNumber);
        }
        return responseStr;
    }

    public String getNetWorkResponseByAmmeterId(Integer ammeterId){
        String responseStr ="-1";
        AmmeterNetwork network = networkMapper.selectByAmmeterId(ammeterId);
        if("".equals(dictionaryService.getDynamicValue(32))){
            return responseStr;
        }
        if(network != null){
            if(new Date().getTime() - network.getCreatedTime().getTime() > 30*60*1000l )  {
                responseStr =  dictionaryService.getDynamicValue(32);
            }
        }else {
            responseStr =  dictionaryService.getDynamicValue(32);
        }
        return responseStr;
    }


    public AmmeterNetwork selectByAmmeterId(Integer ammeterId){
        return networkMapper.selectByAmmeterId(ammeterId);
    }

    public void insertNetWorkInfo(AmmeterDevice device, AmmeterHandleResult result){
        AmmeterNetwork networkDB = networkMapper.selectByAmmeterId(device.getId());
        List<SysDictionary> list = dictionaryService.getDictionartLists();
        if(networkDB != null){
            if(DateUtils.getCurrentDate().equals(networkDB.getRecordDay()) && networkDB.getRecordHour() == DateUtils.getHour()){
                for (SysDictionary sysDictionary:list) {
                    if (sysDictionary.getName().contains("RSSI") && networkDB.getRssi() == null) {
                        networkDB.setRssi(result.getData());
                    } else if (sysDictionary.getName().contains("RSRQ") && networkDB.getRsrq() == null) {
                        networkDB.setRsrq(result.getData());
                    } else if (sysDictionary.getName().contains("CELLI") && networkDB.getCelli() == null) {
                        networkDB.setCelli(result.getData());
                    }
                }
                networkMapper.updateByPrimaryKeySelective(networkDB);
            }
        }
        AmmeterNetwork network = new AmmeterNetwork();
        for (SysDictionary sysDictionary:list) {
            if (sysDictionary.getName().contains("RSSI")) {
                network.setRssi(result.getData());
            } else if (sysDictionary.getName().contains("RSRQ")) {
                network.setRsrq(result.getData());
            } else if (sysDictionary.getName().contains("CELLI")) {
                network.setCelli(result.getData());
            }
        }
        network.setCreatedTime(new Timestamp(new Date().getTime()));
        network.setAmmeterId(device.getId());
        network.setRecordDay(DateUtils.getCurrentDate());
        network.setRecordHour(DateUtils.getHour());
        networkMapper.insert(network);
    }

    public List<AmmeterNetwork>  queryNetWorkParams(AmmeterNetwork network){
        return networkMapper.queryNetWorkParams(network);
    }

    public Page<AmmeterNetWorkResult> networkList(AmmeterNetWorkCondition condition){
        PageHelper.startPage(condition.getPageIndex(),condition.getPageSize());
        return networkMapper.networkList(condition);
    }


    public static void main(String[] args) {
        String param ="meter:0x2800001+rssi:-50+rsrq:-70+cellid:124";
        AmmeterNetwork network = new AmmeterNetwork();
        String[] params = param.split("\\+");

        for (String data:params){
            System.out.println(data);
            if(data != null){
                if(data.contains("rssi:")){
                    System.out.println(data.indexOf("rssi"));
                    network.setRssi(data.substring(5));
                }else if(data.contains("rsrq:")){
                    network.setRsrq(data.substring(5));
                }else if(data.contains("cellid:")){
                    network.setCelli(data.substring(7));
                }
            }
        }
    }
}
