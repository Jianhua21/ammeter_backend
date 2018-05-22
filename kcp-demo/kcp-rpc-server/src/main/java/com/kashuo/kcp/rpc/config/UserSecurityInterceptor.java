package com.kashuo.kcp.rpc.config;

import com.kashuo.kcp.core.AmmeterUserService;
import com.kashuo.kcp.domain.AmmeterLoginHistory;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.rpc.rest.RequestUtil;
import com.kashuo.kcp.utils.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {


    private static final Logger log = LoggerFactory.getLogger(UserSecurityInterceptor.class);

    @Value("${app.constant.loginFlag}")
    private boolean loginFlag;

    @Autowired
    private AmmeterUserService ammeterUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        RequestUtil.setRequest(request);
        String s =request.getHeader("user-agent");
        log.info("user-agent :请求客户端 >>>>{}",s);
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String lesseeId = "1";
        if(loginFlag) {
            lesseeId = request.getHeader("X-USER-TOKEN-ID");
        }
        if (StringUtils.isEmpty(lesseeId)) {
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            log.warn("Headers[X-USER-TOKEN-ID] is required !!!");
            response.setContentType("application/json; charset=utf-8");
            writer.print(Results.error(50000, "请求无效，请尝试重新登录"));
            writer.close();
            return false;
        }

        AmmeterUser user = null;
        if(loginFlag) {
           user = ammeterUserService.checkToken(lesseeId);
        }else{
            user =ammeterUserService.selectByPrimaryKey(Integer.parseInt(lesseeId));
        }
        if (user == null) {
            /***
             * 判断App登陆
             */
            if(s.startsWith("okhttp")){  //App登陆验证
                AmmeterLoginHistory history = ammeterUserService.selectLoginHistoryByToken(lesseeId);
                if(history != null) {
                    user = ammeterUserService.selectByPrimaryKey(history.getUserId());
                }else{
                    response.setCharacterEncoding("utf-8");
                    PrintWriter writer = response.getWriter();
                    response.setContentType("application/json;charset=utf-8");
                    writer.print(Results.error(50000, "请求无效，请尝试重新登录"));
                    writer.close();
                    return false;
                }
            }else {      //网页版登陆验证
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                response.setContentType("application/json;charset=utf-8");
                AmmeterLoginHistory history = ammeterUserService.selectLoginHistoryByToken(lesseeId);
                if (history != null) {
                    writer.print(Results.error(50000, "您的账号已经在别的地方登录，您已经被挤掉线，请重新登录"));
                } else {
                    writer.print(Results.error(50000, "请求无效，请尝试重新登录"));
                }
                writer.close();
                return false;
            }
        }

        RequestUtil.setUser(user);
//        List<String> priCode = userService.loadPriCode(user.getRoleId(),user.getChannelId());
//        RequestUtil.setPrivileges(priCode);

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            log.info("HandlerMethod -> " + handlerMethod.getMethod().toString());
        }

        return true;
    }


}
