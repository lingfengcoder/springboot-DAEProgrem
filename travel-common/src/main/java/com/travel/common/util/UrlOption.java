package com.travel.common.util;

import java.io.UnsupportedEncodingException;

public class UrlOption {
    //URL 编码
    private final static String utfENCODE = "utf-8";
    private final static String GBKEncode = "GBK";

    /**
     * URL 解码
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str)
            return "";
        try {
            result = java.net.URLDecoder.decode(str, utfENCODE);
            result = result.replaceAll(" ", "+");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * URL 转码
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str)
            return "";
        try {
            result = java.net.URLEncoder.encode(str, utfENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
