package com.travel.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpRequestUtil {

    public static String getHttpRequestInfo(HttpServletRequest request){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\r\nURL:" + request.getRequestURL().toString() + "\r\n");
        stringBuffer.append("HTTP_METHOD:" + request.getMethod() + "\r\n");
        stringBuffer.append("IP:" + request.getRemoteAddr() + "\r\n");
        return stringBuffer.toString();
    }
}
