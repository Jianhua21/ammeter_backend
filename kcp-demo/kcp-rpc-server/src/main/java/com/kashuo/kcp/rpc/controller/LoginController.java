package com.kashuo.kcp.rpc.controller;

import com.kashuo.kcp.core.AmmeterChannelService;
import com.kashuo.kcp.core.AmmeterLoginHistoryService;
import com.kashuo.kcp.core.AmmeterUserService;
import com.kashuo.kcp.dao.condition.LoginCondition;
import com.kashuo.kcp.domain.AmmeterChannel;
import com.kashuo.kcp.domain.AmmeterLoginHistory;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.rpc.config.AppConstantProperties;
import com.kashuo.kcp.utils.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 登录
 * Created by Mr.ZHAO on 2016/5/12.
 */
@RestController
@Api(description = "登录")
@RequestMapping(value = "login")
public class LoginController extends BaseController {

    @Value("${website.constant.backendPcesServer}")
    private String backendPcesServer;

    @Autowired
    private AmmeterUserService userService;

//    @Autowired
//    private MenuService menuService;

    @Autowired
    private AmmeterChannelService channelService;

    @Autowired
    private AppConstantProperties properties;

    @Autowired
    private AmmeterLoginHistoryService loginHistoryService;
    @Value("${app.constant.gaode.appId}")
    private String key;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Results login(@RequestBody LoginCondition condition, HttpServletResponse response, HttpServletRequest request) throws IOException {

        if ("".equals(condition.getUsername()) || "".equals(condition.getPassword())) {
            return Results.error("用户名或密码不能为空");
        }

        AmmeterUser user = userService.login(condition.getUsername());
        if (user == null) {
            return Results.error("用户名或手机号不存在");
        }

        String pwdOrToken = condition.getPassword();
        String md5String = EncryptUtils.encryptMD5(pwdOrToken);
        if (!md5String.equals(user.getLoginPasswd()) && !pwdOrToken.equals(user.getToken())) {
            return Results.error("用户名或密码错误");
        }

        if (user.getStatus() != 1) {
            return Results.error("用户状态不可用");
        }

        Integer channelId = user.getChannelId();
        AmmeterChannel channel = channelService.selectByPrimaryKey(channelId);
        String levelCode = channel != null && channel.getLevelCode() != null ? channel.getLevelCode() : "";
        if (channel == null || channelService.foundParentsIsDisable(levelCode)) {
            return Results.error("渠道商已被停用");
        }

        String authToken = new RandomUtils().getRandomString(32, "ilu");
        user.setToken(authToken);
        userService.updateByPrimaryKey(user);

        int liveTime = CookieUtils.LOGIN_SUCESS_REMEMBER_LIVE_TIME;
        // cookieValue = "username#authToken";
        String cookieValue = String.format("%s#%s", user.getLoginName(), authToken);
        if (condition.getRemember()) {
            CookieUtils.addLoginedCookie(response, cookieValue, liveTime);
        }
        String dashboard = "";
        dashboard = StringUtil.isEmpty(dashboard) ? "1,1,1,1,1" : dashboard;
        CookieUtils.addCookie(response, "dashboard", dashboard, liveTime);

        AmmeterLoginHistory history = new AmmeterLoginHistory();
        history.setAccessToken(authToken);
        history.setLoginName(user.getLoginName());
        history.setLoginTime(new Date());
        history.setUserId(user.getId());
        history.setRemoteIp(request.getRemoteAddr());
        loginHistoryService.insertLoginHistory(history,key);

        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("channelId", channelId);
        result.put("authToken", authToken);
        result.put("username", user.getLoginName());
        result.put("trueName", user.getRealname());
        result.put("channelCode", channel.getChannelCode());
        String defaultPassword = properties.getDefaultPassword();
        result.put("defaultpwd", pwdOrToken.equals(defaultPassword));
        return Results.success(result,condition.getSn());
    }

    @RequestMapping(value = "loginout", method = RequestMethod.POST)
    public Results loginOut(@RequestBody LoginCondition condition, HttpServletResponse response) {

        if (!"".equals(condition.getUsername())) {
            AmmeterUser user = userService.login(condition.getUsername());
            if (user != null) {
                user.setToken("");
                userService.updateByPrimaryKey(user);
            }
        }
        return Results.success("success");
    }

    @RequestMapping(value = "loginOutNow", method = RequestMethod.POST)
    public Results loginOutNew(@RequestBody LoginCondition condition, HttpServletResponse response) {
        userService.evictAll();
        return Results.success("success");
    }


}
