package com.travel.common.exception.ExceptionAttribute;


import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

//给容器中添加组件
@Component
public class ExceptionAttribute extends DefaultErrorAttributes implements ExceptionAttributeInterface {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> map = super.getErrorAttributes(webRequest,
                includeStackTrace);

        map.put("__cc__","this is exception info");
        return map;
    }
}
