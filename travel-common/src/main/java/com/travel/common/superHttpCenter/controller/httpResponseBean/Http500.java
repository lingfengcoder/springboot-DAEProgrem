package com.travel.common.superHttpCenter.controller.httpResponseBean;

public class Http500 {

    public  int code=500 ;//状态码
    public  String message="服务器内部错误";//信息
    public boolean encrypt=false;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public Http500(){
    }

    public Http500(String msg){
        this.message=msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
