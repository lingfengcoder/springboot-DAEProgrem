package com.travel.common.config;


import com.travel.common.boot.filter.MyFilter;
import com.travel.common.boot.listener.MyListener;
import com.travel.common.boot.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ServerConfig {


     //注册三大组件
    @Bean//注册自定义servlet
    public ServletRegistrationBean diyServlet(){
        ServletRegistrationBean registrationBean=
                new ServletRegistrationBean(new MyServlet(),"/myServlet");
    return registrationBean;
    }

    @Bean//注册自定义filter
    public FilterRegistrationBean diyFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
        return filterRegistrationBean;
    }

    @Bean//注册监听
    public ServletListenerRegistrationBean diyListener(){
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean(new MyListener());
    return servletListenerRegistrationBean;
    }







}
