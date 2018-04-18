package com.kashuo.kcp.rpc.rest;
import com.kashuo.kcp.domain.AmmeterUser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestUtil {


    private static Log log = LogFactory.getLog(RequestUtil.class);

    private static final ThreadLocal<AmmeterUser> curUserTL = new ThreadLocal<>();

    private static final ThreadLocal<HttpServletRequest> requestTL = new ThreadLocal<>();

    private static final ThreadLocal<List<String>> curUserPrivilegesTL = new ThreadLocal<>();


    /**
     * 获取当前用户
     */
    public static AmmeterUser getCuruser() {
        return curUserTL.get();
    }

    /**
     * 获取当前用户ID
     */
    public static Integer getCuruserId() {
        AmmeterUser lessee = getCuruser();
        return lessee == null ? null : lessee.getId();
    }

    /**
     * 获取当前用户权限代码
     */
    public static List<String> getPrivileges() {
        return curUserPrivilegesTL.get();
    }

    /**
     * 获取httpServletRequest
     */
    public static HttpServletRequest getRequest() {
        HttpServletRequest request = requestTL.get();
        if (request == null) {
            request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            setRequest(request);
        }
        return request;
    }

    /**
     * @param user
     */
    public static void setUser(AmmeterUser user) {
        curUserTL.set(user);
    }

    /**
     * @param privileges
     */
    public static void setPrivileges(List<String> privileges) {
        curUserPrivilegesTL.set(privileges);
    }


    /**
     * @param request
     */
    public static void setRequest(HttpServletRequest request) {
        requestTL.set(request);
    }

    /**
     * 清除当前线程的request对象
     */
    public static void clearRequest() {
        setRequest(null);
    }

    /**
     * 返回全路径url
     */
    public static String getFullURL(HttpServletRequest request) {
        String uri = getURI(request);
        String queryString = request.getQueryString();
        String url = queryString == null ? uri : (uri + "?" + queryString);
        if (log.isDebugEnabled()) {
            log.debug("full url: " + url);
        }
        return url;
    }


    /**
     * 返回不带参数的URI
     */
    public static String getURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    public static String getParameter(String paramName) {
        return getRequest().getParameter(paramName);
    }

    public static Cookie[] getCookies() {
        return getRequest().getCookies();
    }

    public static Cookie getCookie(String cookieName) {
        Cookie[] cookies = RequestUtil.getCookies();
        if (cookies != null) {
            for (int i = cookies.length - 1; i > -1; i--) {
                if (cookieName.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    public static String getCookieValue(String cookieName) {
        Cookie cookie = getCookie(cookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    public static Map<String, String> getParameterMap(String... paramNames) {
        Map<String, String> paramMap = new HashMap<String, String>();
        for (String paramName : paramNames) {
            paramMap.put(paramName, getRequest().getParameter(paramName));
        }
        return paramMap;
    }

    /**
     * 获取项目的contextPath
     *
     * @return
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    public static void printServiceNameInfo() {
        HttpServletRequest request = getRequest();
        log.info("request.getRequestURL: " + request.getRequestURL());
        log.info("X-Real-IP: " + request.getHeader("X-Real-IP"));
        log.info("request.getRemoteAddr(): " + request.getRemoteAddr());
        log.info("x-forwarded-for: " + request.getHeader("x-forwarded-for"));
        log.info("Proxy-Client-IP: " + request.getHeader("Proxy-Client-IP"));
        log.info("WL-Proxy-Client-IP: " + request.getHeader("WL-Proxy-Client-IP"));
    }

    public static String getRemoteIp() {
        printServiceNameInfo();
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}
