package com.travel.common.exception;

import java.util.logging.Logger;

/**
 * 自定义 Exception顶级父类
 */
public class CommonException extends RuntimeException implements ExceptionInterface {

    private String exceptionInfo;
    private Exception e;

    public CommonException(Exception e){
        super(e);
    }
    public CommonException(String message, Exception e) {
        super(e);
        this.exceptionInfo=message;
        this.e = e;
    }

    public CommonException(String message) {
        this.exceptionInfo = message;
    }

    @Override
    public void setExceptionMsg(String msg) {
    }

    @Override
    public void setException(Exception e) {
        this.e=e;
    }

    @Override
    public String getExceptionMsg() {
        return e.getMessage();
    }

    @Override
    public Exception getException() {
        return this.e;
    }

    @Override
    public void printLog(Exception e) {
        e.printStackTrace();

    }

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}
