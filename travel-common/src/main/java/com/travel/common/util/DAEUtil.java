package com.travel.common.util;

import com.travel.common.util.DAE.MD5.MD5Util;
import com.travel.common.util.DAE.RSA.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: wz
 * @Date: 2018/12/21 12:13
 * @Description:
 */
@Component
public class DAEUtil {

    @Autowired
    RSAUtil RSAUtil;
//    RSAUtil RSAUtil=new RSAUtil();
    private final static String LOGIN_STR_PREFIX="travel-login-pwd";

    public  String encryptLoginPassword(String str){
        return MD5Util.encryptByMd5(LOGIN_STR_PREFIX+str);
    }

    /** 对cookie信息进行加密
     * @Description
     * @param: str
     * @return:
     * @auther: wz
     * @date: 2018/12/21 15:12
     */
    public  String encryptCookieInfo(String str){
        try {
            return  RSAUtil.encryptByDefaultPublicKey(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**对cookie信息进行解密
     * @Description
     * @param:
     * @return:
     * @auther: wz
     * @date: 2018/12/21 15:13
     */
    public  String decryptCookieInfo(String str){
        try {
            return  RSAUtil.decryptByDefaultPrivateKey(str);
        } catch (Exception e) {
           // e.printStackTrace();
        return null;
        }
    }
}
