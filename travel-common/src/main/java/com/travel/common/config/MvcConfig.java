package com.travel.common.config;


import com.travel.common.boot.interceptor.InterceptorCtrl;
import com.travel.common.boot.interceptor.OnLineInterceptorCtrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;


/**
 * webmvc 配置类
 */
@Configuration
public class MvcConfig  implements WebMvcConfigurer {

    @Autowired
     OnLineInterceptorCtrl onLineInterceptorCtrl;
    public MvcConfig() {
        //super();
    }

    /**
     * 跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")//get post
                .allowedOrigins("*")
                .maxAge(3600);
    }

    /**
     * 添加自定义视图解析器
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/success").setViewName("/success");
    }

    /**
     * 添加自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //添加登陆拦截器
        registry.addInterceptor(onLineInterceptorCtrl)
                .addPathPatterns("/api/online/**")
                //排除的路由
                .excludePathPatterns("/index.html","/","login.html");
    }

    /**
     * 自定义静态资源访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/travelBack/**")
                .addResourceLocations("classpath:/travelBack/");//添加静态资源路径
    }


    /**
     * 错误处理的 参考源码 ErrorMvcAutoConfiguration （springboot mvc）
     *
     * 1 DefaultErrorAttributes 在错误页面共享信息(存放错误信息(timestamp、status、))
     * 2 BasicErrorController
     * 3 ErrorPageCustomizer
     * 4 DefaultErrorViewResolverConfiguration
     *
     * 步骤:
     * 1 一旦系统出现4XX或者5XX的错误，ErrorPageCustomizer就会生效(定制错误相应规则) 发起/error请求
     *  //
     *   从配置文件中 获取错误页面
     *  @Value("${error.path:/error}")
     *     private String path = "/error";
     *  //
     *
     *  2 发起/error请求后 BasicErrorController处理请求
     *  // @RequestMapping(
     *         produces = {"text/html"}
     *     )
     *     产生html数据
     *  //ResponseEntity
     *     产生JSON数据
     *
     */





}
