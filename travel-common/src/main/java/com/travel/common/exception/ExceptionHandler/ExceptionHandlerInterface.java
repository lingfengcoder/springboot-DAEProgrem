package com.travel.common.exception.ExceptionHandler;

import com.travel.common.superHttpCenter.controller.httpResponseBean.Http500;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public interface ExceptionHandlerInterface {


    Http500 notifyException(Exception e, HttpServletRequest req);
}
