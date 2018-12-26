package com.travel.common.config;


import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConfigurationProperties("druid-boot-config")
public class DruidConfig {


    public static void main(String[] args) throws Exception {
        System.out.println("生成数据库密码");
        ConfigTools.main(new String[]{"fk3FJ*@@f33"});
    }

    private String druidRootName;
    private String druidPassword;
    public String getDruidRootName() {
        return druidRootName;
    }
    public void setDruidRootName(String druidRootName) {
        this.druidRootName = druidRootName;
    }
    public String getDruidPassword() {
        return druidPassword;
    }
    public void setDruidPassword(String druidPassword) {
        this.druidPassword = druidPassword;
    }

    /**
     * 数据源
     * @return
     */
    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource druidSource(){
        DataSource druidDataSource=new DruidDataSource();
       // ((DruidDataSource) druidDataSource).setProxyFilters();
        return druidDataSource;
    }

    //配置druid监控
    @Bean
    public ServletRegistrationBean  setViewServlet(){
        ServletRegistrationBean registrationBean =
                new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParamsMap=new HashMap<>();
        initParamsMap.put("loginUsername",getDruidRootName());
        initParamsMap.put("loginPassword",getDruidPassword());
        initParamsMap.put("allow","");//所有人
        //initParamsMap.put("deny","127.0.0.1");//拒绝
        registrationBean.setInitParameters(initParamsMap);
        return registrationBean;
    }

    //监控的filter
    @Bean
    public FilterRegistrationBean setFilter(){
        FilterRegistrationBean<Filter> bean =
                new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParamsMap=new HashMap<>();
        initParamsMap.put("exclusions","*.js,*.css,*.html,/druid/*");
        bean.setInitParameters(initParamsMap);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }





}
