package com.travel.common.superHttpCenter.controller.httpBean;

import java.util.HashMap;
import java.util.Map;
import static com.travel.common.superHttpCenter.controller.HttpCodeStateCenter.SUCCESS;

public class ResponseDecryptBean {

    private Map<String ,Object> data;
    private Integer code ;//状态码
    private String message;//信息
    private boolean encrypt;//是否需要加密

    public ResponseDecryptBean() {
    }

    public ResponseDecryptBean(Map<String, Object> data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
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

    public static ResponseDecryptBean initResponse(){
        ResponseDecryptBean resp=new ResponseDecryptBean();
        resp.setCode(SUCCESS);
        resp.setData(new HashMap<>());
        resp.setMessage("");
        resp.setEncrypt(false);
        return resp;
    }

    public static ResponseDecryptBean handleResponse(ResponseDecryptBean resp, Object ...args){
        for(Object o:args){
            if(o==null)continue;
            if(o.getClass().equals(Integer.class)){
                resp.setCode((Integer) o);
            }
            else if(o.getClass().equals(HashMap.class)){
                resp.setData((HashMap)o);
            }
            else if(o.getClass().equals(String.class)){
                resp.setMessage((String)o);
            }else{
                //默认封装data
                Map map=new HashMap();
                String key=o.getClass().getName();
                key=key.substring(key.lastIndexOf(".")+1).toLowerCase();
                map.put(key,o);
                resp.setData(map);
            }
        }
        return resp;
    }

}
