package com.kashuo.kcp.rpc.config;

import com.kashuo.kcp.core.AmmeterUserService;
import com.kashuo.kcp.domain.AmmeterUser;
import com.kashuo.kcp.rpc.rest.RequestUtil;
import com.kashuo.kcp.utils.Results;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class UserSecurityInterceptor extends HandlerInterceptorAdapter {


    private static final Log log = LogFactory.getLog(UserSecurityInterceptor.class);

    @Autowired
    private AmmeterUserService ammeterUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        RequestUtil.setRequest(request);
        String method = request.getMethod();
        if ("OPTIONS".equals(method)) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        String lesseeId = request.getHeader("X-USER-TOKEN-ID");
        if (StringUtils.isEmpty(lesseeId)) {
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            log.warn("Headers[X-USER-TOKEN-ID] is required !!!");
            response.setContentType("application/json; charset=utf-8");
            writer.print(Results.error(50000, "请求无效，请尝试重新登录"));
            writer.close();
            return false;
        }

        AmmeterUser user = ammeterUserService.checkToken(lesseeId);
        if (user == null) {
            response.setCharacterEncoding("utf-8");
            PrintWriter writer = response.getWriter();
            response.setContentType("application/json;charset=utf-8");
            writer.print(Results.error(50000, "请求无效，请尝试重新登录"));
            writer.close();
            return false;
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
