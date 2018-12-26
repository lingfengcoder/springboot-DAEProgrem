package com.travel.common.superHttpCenter.controller.httpBean;

import com.alibaba.fastjson.JSONObject;
import com.travel.common.util.DAE.bean.AESBean;

/**
 * 定义解密后数据
 */
public class RequestDecryptBean {
    private JSONObject data;
    private String timeStamp;//时间戳
    private AESBean aesBean;//aes key-iv
    private String signature;//数据签名



    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public AESBean getAesBean() {
        return aesBean;
    }

    public void setAesBean(AESBean aesBean) {
        this.aesBean = aesBean;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
