package com.travel.common.annotation.DAEAspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import static com.travel.common.util.FileType.NO_ALLOW;

@Target({ElementType.METHOD})//作用域(method...)//ElementType.TYPE,
@Retention(RetentionPolicy.RUNTIME)//定义注解生命周期(运行时)
public @interface DAEAnnotation {
    String DAEType() default "";//加密解密方法
    boolean needEncrypt() default true;//是否需要加密
    boolean needDecrypt() default true;//是否需要解密
    boolean needLog() default  false;
    String allowFileType() default NO_ALLOW;//上传文件类型
    String []allowFileTypes() default NO_ALLOW;//自定义上传文件类型
}
