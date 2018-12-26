package com.travel.common.superHttpCenter.DAEService;

public interface DAEServiceInterface<D,A,R,P> {

     //解密
      boolean checkTimeStamp(D d, A a);//检查时间戳
      boolean checkSignature(D d,A a);//检查签名
      A decryptKey(D d) throws Exception;//解密key-iv
      R decryptData(D d) throws Exception;//解密数据

     //加密
      P encryptData(A a,Object...srcData) throws Exception;//response 加密



}
