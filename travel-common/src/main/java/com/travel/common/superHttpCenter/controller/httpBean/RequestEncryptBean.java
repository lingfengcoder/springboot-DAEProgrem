package com.travel.common.superHttpCenter.controller.httpBean;

import com.travel.common.util.DAE.bean.AESBean;

/**
 * 定义http加密数据格式
 */
public class RequestEncryptBean {
  private String data;//加密后的数据
  private String timeStamp;//时间戳
  private AESBean aesBean;//aes key-iv
  private String aesBeanStr;//aes json
  private String signature;//数据签名

  public String getAesBeanStr() {
    return aesBeanStr;
  }

  public void setAesBeanStr(String aesBeanStr) {
    this.aesBeanStr = aesBeanStr;
  }

  public String getTimeStamp() {
    return timeStamp;
  }
  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }
  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public AESBean getAesBean() {
    return aesBean;
  }

  public void setAesBean(AESBean aesBean) {
    this.aesBean = aesBean;
  }
}
