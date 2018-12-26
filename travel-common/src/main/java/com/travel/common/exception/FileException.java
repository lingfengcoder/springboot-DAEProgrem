package com.travel.common.exception;

public class FileException extends CommonException {

    public FileException(String message, Exception e) {
        super(message, e);
    }

//    public FileException(String message, Throwable cause, String msg, Exception e) {
////        //super(message, cause, msg, e);
////    }

    public FileException(String message) {
        super(message);
    }

    @Override
    public void setExceptionMsg(String msg) {
        super.setExceptionMsg(msg);
    }

    @Override
    public void setException(Exception e) {
        super.setException(e);
    }

    @Override
    public String getExceptionMsg() {
        return super.getExceptionMsg();
    }

    @Override
    public Exception getException() {
        return super.getException();
    }
}
