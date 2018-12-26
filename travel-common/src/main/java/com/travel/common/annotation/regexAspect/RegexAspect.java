package com.travel.common.annotation.regexAspect;

import com.travel.common.annotation.AspectInterface;
import com.travel.common.exception.serviceException.ServiceException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Auther: wz
 * @Date: 2018/12/24 13:19
 * @Description:
 */
@Component
public class RegexAspect implements AspectInterface {
    Map<String, String> map = new ConcurrentHashMap<>();
    StringBuffer stringBuffer=new StringBuffer();
    public boolean doFilter(Object obj){
        Map map=doFilterRETMap(obj);
        return map.isEmpty();
    }

    public Map<String, String> doFilterRETMap(Object obj){
        if(!map.isEmpty())map.clear();
        if(stringBuffer.length()>0)stringBuffer.setLength(0);
        Class<?> regexFilterClass = obj.getClass();
        Field[] fields = regexFilterClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(RegexFilter.class)) {
                if(stringBuffer.length()>0)stringBuffer.setLength(0);
                //获取private权限
                field.setAccessible(true);
                RegexFilter regexFilter = field.getDeclaredAnnotation(RegexFilter.class);
                //获取属性
                String name = field.getName();
                //获取属性值
                String value = null;
                String regex=regexFilter.regex();
                boolean isCanlog=regexFilter.isCanlog();
                try {
                    value =String.valueOf(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    stringBuffer.append(obj.getClass());
                    stringBuffer.append(" ");
                    stringBuffer.append(field.getName());
                    stringBuffer.append(" ");
                    stringBuffer.append(regex);
                    logger.error(stringBuffer.toString());
                    map.put(field.getName(),"regex:"+regex);
                }
                if(value==null||value.equals("null")){
                    if(regexFilter.necessary())map.put(field.getName(),value+"regex:"+regex);
                }else{
                    //Class type=regexFilter.type();
                    if(!Pattern.matches(regex,value)){
                        stringBuffer.setLength(0);
                        stringBuffer.append(obj.getClass());
                        stringBuffer.append(" ");
                        stringBuffer.append(field.getName());
                        stringBuffer.append(" ");
                        stringBuffer.append(regex);
                        if(isCanlog){
                            stringBuffer.append(" value:");
                            stringBuffer.append(value);
                        }
                        logger.error(stringBuffer.toString());
                        map.put(field.getName(),value+"regex:"+regex);
                    }
                }
            }
        }
        return map;
    }



}
