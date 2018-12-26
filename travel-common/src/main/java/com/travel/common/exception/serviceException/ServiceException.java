package com.travel.common.exception.serviceException;

import com.travel.common.exception.CommonException;

public class ServiceException extends CommonException {
    public ServiceException(String message, Exception e) {
        super(message,e);
    }
    public ServiceException(String message) {
        super(message);
    }
}
