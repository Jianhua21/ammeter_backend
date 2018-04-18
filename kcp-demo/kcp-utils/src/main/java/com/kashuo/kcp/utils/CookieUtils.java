package com.kashuo.kcp.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class CookieUtils {

    public static String LOGIN_SUCESS_COOKIE = "zdop_login_sucess";

    /**
     * 10分钟
     */
    public static int LOGIN_SUCESS_DEFAULT_LIVE_TIME = 60 * 10;

    /**
     * 7天
     */
    public static int LOGIN_SUCESS_REMEMBER_LIVE_TIME = 60 * 60 * 24 * 7;


    /**
     * 设置登录成功后cookie
     */
    public static void addLoginedCookie(HttpServletResponse response, String value) {
        addCookie(response, LOGIN_SUCESS_COOKIE, value, LOGIN_SUCESS_DEFAULT_LIVE_TIME);
    }

    /**
     * 设置登录成功后cookie
     */
    public static void addLoginedCookie(HttpServletResponse response, String value, int maxAge) {
        addCookie(response, LOGIN_SUCESS_COOKIE, value, maxAge);
    }

    /**
     * 设置cookie
     *
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, URLEncoder.encode(value));
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     *
     * @param name cookie名字
     */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
