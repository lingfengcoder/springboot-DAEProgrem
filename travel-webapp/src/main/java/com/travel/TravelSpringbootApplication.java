package com.travel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan//使用 自定义servlet扫描器
@SpringBootApplication
//@MapperScan("com.travel.*.mapper.*")
//@ComponentScan用于配置扫描com.travel.web之外的包下面的类
public class TravelSpringbootApplication {

    public static void main(String[] args) {
//        SpringApplication sa=new SpringApplication(WyaitWebApplication.class);
//        // 禁用devTools热部署
//        //System.setProperty("spring.devtools.restart.enabled", "false");
//        // 禁用命令行更改application.properties属性
//        sa.setAddCommandLineProperties(false);
//        sa.run(args);
        SpringApplication.run(TravelSpringbootApplication.class, args);
    }
}
