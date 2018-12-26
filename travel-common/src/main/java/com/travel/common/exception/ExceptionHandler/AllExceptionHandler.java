package com.travel.common.exception.ExceptionHandler;

import com.travel.common.exception.CommonException;
import com.travel.common.superHttpCenter.controller.httpResponseBean.Http500;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.travel.common.util.HttpRequestUtil.getHttpRequestInfo;

/**
 * 统一异常处理器
 */
@ControllerAdvice(basePackages = "com.travel")
public class AllExceptionHandler implements ExceptionHandlerInterface{

    Logger logger = LoggerFactory.getLogger(getClass());
    StringBuffer errorMsg = new StringBuffer();
    @ResponseBody//将错误信息返回出去
    @ExceptionHandler(Exception.class)
    @Override
    public Http500 notifyException(Exception e, HttpServletRequest req) {
        //获取请求中的信息
        logger.info(getHttpRequestInfo(req));
        //e.printStackTrace();
        if(e instanceof CommonException){//如果是自定义异常
            CommonException commonException=(CommonException) e;
            logger.error(commonException.getExceptionInfo());
            logError(e);
            return new Http500(commonException.getExceptionInfo());
        }else{
            logError(e);
        }
        return new Http500();
    }

    private void logError(Exception e){
        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement s : trace)errorMsg.append("\tat " + s + "\r\n");
        logger.error(e.getMessage()+errorMsg.toString());
        errorMsg.setLength(0);
    }

}
