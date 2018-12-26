package com.travel.common.exception;


public interface ExceptionInterface {

    void setExceptionMsg(String msg);
    void setException(Exception e);
    String getExceptionMsg();
    Exception getException();
    void printLog(Exception e);
}
