package com.travel.common.exception;

public class ToC_Exception extends CommonException {

    public ToC_Exception(String message, Exception e) {
        super(message,e);
    }
    public ToC_Exception(String message) {
        super(message);
    }
    public ToC_Exception() {
        super(new Exception().getMessage());
    }
}
