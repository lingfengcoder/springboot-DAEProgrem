package com.travel.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.Reflection;

/**
 * @Auther: wz
 * @Date: 2018/12/24 13:11
 * @Description:
 */
public class LogUtil {


    private static Logger logger = null;
    public static Logger getInitLogger(){
        return logger;
    }
    public static  Logger getLogger() {
        if (null == logger) {
            //Java8 废弃了Reflection.getCallerClass()
             logger = LoggerFactory.getLogger(Reflection.getCallerClass());
            //logger.debug("调用者类名" + Reflection.getCallerClass().getName());
        }
        return logger;
    }

}
