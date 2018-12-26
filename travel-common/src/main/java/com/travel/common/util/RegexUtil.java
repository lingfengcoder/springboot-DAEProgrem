package com.travel.common.util;

import com.travel.common.annotation.regexAspect.RegexAspect;
import com.travel.common.annotation.regexAspect.RegexFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Auther: wz
 * @Date: 2018/12/24 11:53
 * @Description:
 */
@Component
public class RegexUtil {

    public final static String password_REGEX = "^[A-Za-z0-9_]{6,10}$";
    public final static String username_REGEX = "^[A-Za-z0-9_]{6,10}$";
    public final static String email_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public final static String phone_REGEX = "^\\d{11}$";


    @Autowired
    RegexAspect  regexAspect;

    public  RegexUtil matches(String regex,String target){
        if(Pattern.matches(regex,target));
        return this;
    }

    public boolean regexFilter(Object obj){
        return regexAspect.doFilter(obj);
    }

    public String regexFilter_String(Object obj){
        StringBuffer stringBuffer=new StringBuffer();
        Map map =regexAspect.doFilterRETMap(obj);
        map.forEach((key,value)->{
            stringBuffer.append(" ");
            stringBuffer.append(key);
            stringBuffer.append("格式有误");
        });
        return stringBuffer.toString();
    }

}
