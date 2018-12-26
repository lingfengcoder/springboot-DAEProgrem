package com.travel.common.superHttpCenter.controller.httpResponseBean;

public class Http400 {
    public  int code=400 ;//状态码
    public  String message="客户端错误";//信息
    public Http400(){
    }

    public Http400(String msg){
        this.message=msg;
    }
    public Http400(int code,String message){
        this.code=code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEncrypt() {
        return encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public boolean encrypt=false;

}
