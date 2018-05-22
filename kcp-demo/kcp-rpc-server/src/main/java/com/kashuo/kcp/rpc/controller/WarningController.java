package com.kashuo.kcp.rpc.controller;

import com.alibaba.fastjson.JSON;
import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterService;
import com.kashuo.kcp.core.AmmeterWarningService;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.dao.result.WarningHome;
import com.kashuo.kcp.domain.AmmeterDevice;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.domain.AmmeterWarning;
import com.kashuo.kcp.domain.AmmeterWarningResult;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dell-pc on 2018/4/10.
 */
@RestController
@Api("报警管理")
@RequestMapping("/warning")
public class WarningController extends BaseController{

    private static Logger logger = LoggerFactory.getLogger(WarningController.class);
    @Autowired
    private AmmeterWarningService warningService;

    @Autowired
    private AmmeterService ammeterService;


    @PostMapping("/home")
    @ApiOperation("告警总览")
    public Results getWarningHome() throws Exception {
        AmmeterUser user = getCuruser();
        WarningHome warningHome = warningService.reportWarningInfo();
        logger.info("告警总览数据 :{}",JSON.toJSONString(warningHome));
        return  Results.success(warningHome);
    }


    @PostMapping("/list")
    @ApiOperation(value = "列表查询",notes = "created by Legend on 2018-Apr-10")
    public Results list(@RequestBody WarningCondition warningCondition){
        logger.info("告警列表查询参数:{}", JSON.toJSONString(warningCondition));
        if(isAdmin(getCuruser().getChannelId())){
            warningCondition.setChannelId(null);
        }else {
            warningCondition.setChannelId(getCuruser().getChannelId());
        }
        Page<AmmeterWarningResult> pages = warningService.queryWarningList(warningCondition);
        if(pages != null){
            Results results = new Results(pages);
            results.setTotal(pages.getTotal());
            results.setSn(warningCondition.getSn());
            return results;
        }
        return Results.success("获取列表没数据!");
    }

    /***
     * 消除警告
     * @param warningId
     * @param sn
     * @return
     */
    @GetMapping("/avoid/{warningId}/{sn}")
    public Results avoidWarning(@PathVariable("warningId") Integer warningId,@PathVariable("sn") String sn){
        AmmeterWarning  warningDB = warningService.selectWarningByKey(warningId);
        if(warningDB == null){
            return Results.error("该警告不存在,请确认!",sn);
        }
        AmmeterWarning warning = new AmmeterWarning();
        warning.setId(warningId);
        warning.setWarningStatus("1");
        Integer result = warningService.updateWarning(warning);
        if(warningDB.getWarningType() == 0) {
            AmmeterDevice device = ammeterService.selectByPrimaryKey(warningDB.getId());
            if (device != null) {
                AmmeterDevice device_2 = new AmmeterDevice();
                device_2.setRsrqWarningFlag(0);
                device_2.setId(device.getId());
                ammeterService.updateWarningStatusByPrimaryKey(device_2);
            }
        }
        if(result >0){
            return Results.success("该警告已消除!",sn);
        }
        return Results.error("警告消除失败!",sn);
    }

    /***
     * 恢复警告
     * @param warningId
     * @param sn
     * @return
     */
    @GetMapping("/restore/{warningId}/{sn}")
    public Results restoreWarning(@PathVariable("warningId") Integer warningId,@PathVariable("sn") String sn){
        AmmeterWarning  warningDB = warningService.selectWarningByKey(warningId);
        if(warningDB == null){
            return Results.error("该警告不存在,请确认!",sn);
        }
        AmmeterWarning warning = new AmmeterWarning();
        warning.setId(warningId);
        warning.setWarningStatus("0");
        Integer result = warningService.updateWarning(warning);
        if(result >0){
            return Results.success("该警告已恢复!",sn);
        }
        return Results.error("警告恢复失败!",sn);
    }
}
