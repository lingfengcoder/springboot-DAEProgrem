package com.travel.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Auther: wz
 * @Date: 2018/12/20 17:36
 * @Description:
 */
public class IPUtil {

    public static String getLocationIp()  {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "";
        }
        return address.getHostAddress();
    }
}
