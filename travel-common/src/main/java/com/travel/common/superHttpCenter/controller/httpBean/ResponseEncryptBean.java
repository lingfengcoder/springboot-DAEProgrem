package com.travel.common.superHttpCenter.controller.httpBean;

public class ResponseEncryptBean {

    private String data;
    private String code ;//状态码
    private String message;//信息
    private boolean encrypt=true;//是否加密

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
