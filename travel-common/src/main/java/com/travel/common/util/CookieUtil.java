package com.travel.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CookieUtil {

    //最大存活时间
    private final static int COOKIE_MAX_LIVE_TIME=7*24*3600;//单位:秒

    private static HttpServletResponse response;

    @Autowired
    private HttpServletResponse response2;

    private static HttpServletRequest request;

    @Autowired
    private HttpServletRequest request2;

    /**
     * @Description servlet执行init()方法之前执行
     * @param:
     * @return:
     * @auther: wz
     * @date: 2018/12/19 12:07
     */
    @PostConstruct
    public void beforeInit() {
        request=request2;
        response=response2;
    }
    /**
     * 根据Cookie名称得到Cookie对象，不存在该对象则返回Null
     * @param name
     * @return Cookie
     */
    public static Cookie getCookie(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies==null||cookies.length<1) {
            return null;
        }
        Cookie cookie = null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) {
                cookie = c;
                break;
            }
        }
        return cookie;
    }

    /**
     * 根据Cookie名称直接得到Cookie值
     * @param name
     * @return
     */
    public static String getCookieValue(String name) {
        Cookie cookie = getCookie(name);
        if(cookie != null){
            return cookie.getValue();
        }
        return null;
    }

    /**
     * 移除cookie
     * @param name 这个是名称，不是值
     */
    public static void removeCookie(String name) {
        if (null == name) {
            return;
        }
        Cookie cookie = getCookie(name);
        if(null != cookie){
            cookie.setPath("/");
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }


    /**
     * 添加一条新的Cookie，可以指定过期时间(单位：秒)
     * @param name
     * @param value
     * @param maxValue
     */
    public static void setCookie(String name,
                                 String value, int maxValue) {
        if (StringUtils.isEmpty(name)) {
            return;
        }
        if (null == value) {
            value = "";
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        //cookie.setDomain(IPUtil.getLocationIp());
        if (maxValue != 0) {
            cookie.setMaxAge(maxValue);
        } else {
            cookie.setMaxAge(COOKIE_MAX_LIVE_TIME);
        }
        response.addCookie(cookie);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一条新的Cookie
     *
     * @param name
     * @param value
     */
    public static void setCookie(String name,
                                 String value) {
        setCookie(name, value, COOKIE_MAX_LIVE_TIME);
    }


}
