package com.kashuo.kcp.rpc.controller;

import com.kashuo.common.base.domain.Page;
import com.kashuo.kcp.core.AmmeterWarningService;
import com.kashuo.kcp.dao.condition.WarningCondition;
import com.kashuo.kcp.domain.AmmeterWarningResult;
import com.kashuo.kcp.utils.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dell-pc on 2018/4/10.
 */
@RestController
@Api("报警管理")
@RequestMapping("/warning")
public class WarningController extends BaseController{

    @Autowired
    private AmmeterWarningService warningService;

    @PostMapping("/list")
    @ApiOperation(value = "列表查询",notes = "created by Legend on 2018-Apr-10")
    public Results list(@RequestBody WarningCondition warningCondition){
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
}
