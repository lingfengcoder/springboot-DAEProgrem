package com.travel.common.superHttpCenter.controller;

import com.travel.common.superHttpCenter.controller.httpBean.ResponseDecryptBean;
import com.travel.common.util.DAEUtil;
import com.travel.common.util.LogUtil;
import com.travel.common.util.RegexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.travel.common.superHttpCenter.controller.HttpCodeStateCenter.*;
import static com.travel.common.superHttpCenter.controller.httpBean.ResponseDecryptBean.handleResponse;
import static com.travel.common.superHttpCenter.controller.httpBean.ResponseDecryptBean.initResponse;

@Component
public abstract class HttpInterfaceImpl implements HttpInterface<ResponseDecryptBean> {

    @Autowired
    protected DAEUtil daeUtil;
    @Autowired
    protected RegexUtil regexUtil;

    public final static String DAEParam="_decryptData_";
    public Logger logger =  LoggerFactory.getLogger(getClass());

    @Override
    public ResponseDecryptBean SUCCESS(Object... args) {
        ResponseDecryptBean resp=initResponse();
        resp.setCode(SUCCESS);
        resp=handleResponse(resp,args);
        return resp;
    }

    @Override
    public ResponseDecryptBean FAIL(Object... args) {
        ResponseDecryptBean resp=initResponse();
        resp.setCode(FAIL_400);
        resp=handleResponse(resp,args);
        return resp;
    }

    @Override
    public ResponseDecryptBean ERROR(Object... args) {
        ResponseDecryptBean resp=initResponse();
        resp.setCode(ERROR);
        resp=handleResponse(resp,args);
        return resp;
    }


}
