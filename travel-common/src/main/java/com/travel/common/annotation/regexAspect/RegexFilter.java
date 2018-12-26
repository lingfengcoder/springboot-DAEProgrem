package com.travel.common.annotation.regexAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: wz
 * @Date: 2018/12/24 12:22
 * @Description:
 */
@Target({ElementType.TYPE,ElementType.METHOD,ElementType.FIELD})//作用域(class method...)
@Retention(RetentionPolicy.RUNTIME)//定义注解生命周期(运行时)
public @interface RegexFilter {
    String regex();
    boolean necessary() default false;
    boolean isCanlog() default true;
    Class type() default String.class;
}
