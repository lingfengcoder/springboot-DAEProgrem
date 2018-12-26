package com.travel.common.util.DAE.RSA;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import com.travel.common.util.FileOption;
import com.travel.common.util.UrlOption;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RSAUtil {



    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";
    public static final String SIGN = "sign";
    public static final String WSIGN = "wRz";
    public static final String PUBLICKEY = "publicKey";
    private static final String PRIVATE_KEY_PATH="/WEB-INF/app_RSA.txt";
    private static final String PUBLIC_KEY_PATH="/WEB-INF/app_RSAPubK.txt";


    public static void main(String[] args) {

        RSAUtil rsaUtil = new RSAUtil();
        System.out.println(getServicePath());
        KeyPair kp=rsaUtil.getKey();
        String srcStr="这是中文333@##dsf分胜多负少";

        String privateKey=kp.getPrivate().toString();
        System.out.println("privateKey: "+privateKey);

        String publicKey= null;
        try {
            publicKey = rsaUtil.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("publicKey:"+publicKey);


        String encodeStr = null;
        try {
            encodeStr = rsaUtil.encryptByPublicKeyRetStr(srcStr,publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("加密后："+encodeStr);

        String decodeStr = null;
        try {
            decodeStr = rsaUtil.decryptByPrivateKeyForInternet(encodeStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("解密后："+decodeStr);
    }
    /**
     * 用指定私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key) throws Exception {
        if(data==null)return null;
        return new String(decryptByPrivateKey(decryptBASE64(data), key));
    }

    /**
     * 用默认私钥解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String decryptByDefaultPrivateKey(String data) throws Exception {
        if(data==null)return null;
        String key = getPrivateKey();
        return new String(decryptByPrivateKey(decryptBASE64(data), key));
    }

    /**
     * 私钥解密  用于网络传输的数据
     *
     * @param
     * @return String
     * @throws Exception
     */
    public String decryptByPrivateKeyForInternet(String data) throws Exception {
        if(data==null)return null;
        String key = getPrivateKey();
        // url解码
        data = UrlOption.getURLDecoderString(data);
        // System.out.println(data);
        return new String(decryptByPrivateKey(decryptBASE64(data), key));
    }

    // 对密钥解密
    public static byte[] decryptByPrivateKey(byte[] data, String key) throws Exception {

        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return byte[]
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data.getBytes());
    }

    /**
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return String
     * @throws Exception
     */
    public String encryptByPublicKeyRetStr(String data, String key) throws Exception {
        if(data==null)return null;
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }
    /**
     * @Description 使用默认公钥加密
     * @param:
     * @return:
     * @auther: wz
     * @date: 2018/12/21 15:09
     */
    public String encryptByDefaultPublicKey(String data) throws Exception {
        if(data==null)return null;
        String key=getPublicKey();
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);
        System.out.println();
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
        byte[] keyBytes = decryptBASE64(key);
        System.out.println();
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * @Description 使用默认私钥加密
     * @param: data数据 key私钥
     * @return: String
     * @auther: wz
     * @date: 2018/12/21 14:09
     */
    public String encryptByDefaultPrivateKeyRetStr(String data)throws Exception{
        String key=getPrivateKey();
       return new String(encryptByPrivateKey(data.getBytes(),key));
    }
    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key) throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 取得私钥
     *
     * @param
     * @return
     * @throws Exception
     */
    public String getPrivateKey() throws Exception {
        KeyPair keyPair = getKey();
        Key key = keyPair.getPrivate();
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param
     * @return
     * @throws Exception
     */
    public String getPublicKey() throws Exception {
        KeyPair keyPair = getKey();
        Key key = keyPair.getPublic();
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 生成密钥
     *
     * @return
     * @throws Exception
     */
    public Map<String, Key> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 存入文件
        saveKey(keyPair);
        Map<String, Key> keyMap = new HashMap<String, Key>(2);
        keyMap.put(PUBLIC_KEY, keyPair.getPublic());// 公钥
        keyMap.put(PRIVATE_KEY, keyPair.getPrivate());// 私钥
        return keyMap;
    }


    /**
     * 本地化 密钥
     *
     * @param keyPair
     */
    public void saveKey(KeyPair keyPair) {
        String path = getServicePath() + PRIVATE_KEY_PATH;
        FileOption.writeObj(keyPair, path);
        String publicKeyPath = getServicePath() + PUBLIC_KEY_PATH;
        FileOption.write(encryptBASE64(keyPair.getPublic().getEncoded()), publicKeyPath, false);
    }

    /**
     * 判断RSA是否存在
     *
     * @return
     */
    public boolean existsRSA() {
        String path = getServicePath() + PRIVATE_KEY_PATH;
        return FileOption.existsFile(path);
    }

    public static byte[] decryptBASE64(String key) {
        return Base64.decodeBase64(key);
    }

    public static String encryptBASE64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public String servicePath() {
        String servicePath = this.getClass().getResource("/").toString().split("WEB-INF")[0];
        //return servicePath = servicePath.substring(6, servicePath.length() - 1);
        System.out.println(servicePath);
        return "C:/TEAVEL/";
    }

    public static String getServicePath() {
        return new RSAUtil().servicePath();
    }

    /**
     * 获取密钥
     *
     * @return
     */
    public  KeyPair getKey() {
        Object obj = null;
        try {
            String path = getServicePath() + PRIVATE_KEY_PATH;
            obj = FileOption.readObj(path);
            if (obj == null) {// 公钥不存在
                System.out.println("公钥不存在 重新生成!");
                // 生成
                initKey();
                obj = getKey();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (KeyPair) obj;
    }

    // 将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }





    /*
     * //将base64编码后的私钥字符串转成PrivateKey实例 public static PrivateKey
     * getPrivateKey(String privateKey) throws Exception{ byte[ ]
     * keyBytes=Base64.getDecoder().decode(privateKey.getBytes());
     * PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes); KeyFactory
     * keyFactory=KeyFactory.getInstance("RSA"); return
     * keyFactory.generatePrivate(keySpec); }
     */

    /*
     * public static void main(String[] args) { String publicKey =
     * "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPlwWsFjROEdY49u9KFxUIPQH/1UfYgpC+/dPXFiEakJfgx+FBs1VjQ8595WCrSQV4PbF6rJJyhFFDJmwLJpGb9p7ZWCvl2ALjPM9W6F1ga/2pztVnZMFffn+Z5Xr7RptfNsg8dfI+wPfMBy6VAaFnOUK8ePeXqMd/JR6PDe2O6wIDAQAB";
     * String privateKey =
     * "MIICWwIBAAKBgQDFZhUwtiWIGXQks/DsDA9RfAupn8Yk5UPbEFaOkDW73smL/cRxtQvhiLV1wuspJ1aCg+rzQGoyraWfkVrhMdbNp8lCJHXA5wJIoySU76YfcZh9hJEWlJjPHahW7WHJF4oLKFD+pD2OiIevvGg7wNsIZACLtkTVINKyA/K+yF2wiwIDAQABAoGAMhfIstbWSWZkMdg0AELDNGNtNjtkdgJ0KMhJnt37tNRBgzP/wPc3r6EYo2y2bngcHPzLB7XF/VxRll+1l0YlgxIFQ9ZxDuEHxP601JkiuqgzKFKIraqBlGt/aovLMRZeMTbimBgoc4kulLQgZa5QbgMbsNKWteOOuMKTbJcWkQkCQQD8A6/WQc+EGHmkeHe/xG6YxG6o/m9BjKbEl9NzE2asnpdIOG4BPz2fx0IwUN/NQNbCg0Dl9x56DgOBFo/bAZgFAkEAyIVHAfqJpujK0OV6k5ePxMYyRCcJ2g4hCPpBNToJbCWZI/dkvWY5HgpM5z8nJJDIpIbCjRMLK4HyyihIkF5bTwJAKplc8bSyJTwV481RQKMtprkdk4deuw/RaM3ZOkx/QJKWM+kF/0P3YLjH4W7qRcG+C6cwIy9AgZU41ms/6d89GQJASp//KydYbzuXr2KX2bHAGZVBQ8fbjMFRXgv0lKCCvTLI6W+6mBohC6L9t9ny8VLIuSJF6Ua7E3gLKV0QlFnpVQJAYHD2ilcIfkoQQTEPUUnl0xZ4F3IAt1bwopfrHQJ/kkJw8tOqCRbh/jeJYFlc0F3L+hdn57FkChtnljAqfFmyLQ==";
     * StringBuffer strbPrivateKey=new StringBuffer(); privateKey=
     * "MIICdAIBADANBgkqhkiG9w0BAQEFAASCAl4wggJaAgEAAoGAfDJmrnOayzyfgeQq"+
     *
     * "4leCBlW+4co="; String inputStr = "abc"; String miwan =
     * "WjBxtdyKJiRJRXyxF9OrGep7GHabQ/2j77b2LmieR7Wqu89ngQpwpHnD4U9LFTBcLQa9kjGmOcr6nReHfmCT2iPfhcnZIUSMPIkb5iC7m9b2WGmgc94jkd6YxgK8CESDZP9GCiAPRevhsJj1+Qsv6W3V6Ncxp0rBBvglwe6I/TU=";
     * Map<String, Key> keyMap; try {
     *
     * String
     * randomStr=Base64.encodeBase64String((""+Math.random()/1000*1000).getBytes());
     * System.out.println(randomStr);
     *
     * getPublicKey(publicKey);
     *
     * keyMap = AppSimpleRSAUtil.initKey(); publicKey =
     * AppSimpleRSAUtil.getPublicKey(keyMap); privateKey =
     * AppSimpleRSAUtil.getPrivateKey(keyMap); System.err.println("公钥: \n\r" +
     * publicKey); //System.err.println("私钥： \n\r" + privateKey);
     * System.err.println("公钥加密——私钥解密"); byte[] encodedData =
     * AppSimpleRSAUtil.encryptByPublicKey(inputStr, publicKey);
     *
     * System.out.println(Base64.encodeBase64String(encodedData));
     *
     * String decodedData = AppSimpleRSAUtil.decryptByPrivateKey(miwan, privateKey);
     * String outputStr = new String(decodedData); System.err.println("加密前: " +
     * inputStr + "\n\r" + "解密后: " + outputStr);
     *
     * } catch (Exception e) { // TODO Auto-generated catch block
     * e.printStackTrace(); }
     *
     * }
     */

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data
     *            加密数据
     * @param privateKey
     *            私钥
     * @return
     * @throws Exception
     */
    /*
     * public static String sign(byte[] data, String privateKey) throws Exception {
     * // 解密由base64编码的私钥 byte[] keyBytes = decryptBASE64(privateKey); //
     * 构造PKCS8EncodedKeySpec对象 PKCS8EncodedKeySpec pkcs8KeySpec = new
     * PKCS8EncodedKeySpec(keyBytes); // KEY_ALGORITHM 指定的加密算法 KeyFactory keyFactory
     * = KeyFactory.getInstance(KEY_ALGORITHM); // 取私钥匙对象 PrivateKey priKey =
     * keyFactory.generatePrivate(pkcs8KeySpec); // 用私钥对信息生成数字签名 Signature signature
     * = Signature.getInstance(SIGNATURE_ALGORITHM); signature.initSign(priKey);
     * signature.update(data); return encryptBASE64(signature.sign()); }
     */

    /**
     * 校验数字签名
     *
     * @param data
     *            加密数据
     * @param publicKey
     *            公钥
     * @param sign
     *            数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    /*
     * public static boolean verify(byte[] data, String publicKey, String sign)
     * throws Exception { // 解密由base64编码的公钥 byte[] keyBytes =
     * decryptBASE64(publicKey); // 构造X509EncodedKeySpec对象 X509EncodedKeySpec
     * keySpec = new X509EncodedKeySpec(keyBytes); // KEY_ALGORITHM 指定的加密算法
     * KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM); // 取公钥匙对象
     * PublicKey pubKey = keyFactory.generatePublic(keySpec); Signature signature =
     * Signature.getInstance(SIGNATURE_ALGORITHM); signature.initVerify(pubKey);
     * signature.update(data); // 验证签名是否正常 return
     * signature.verify(decryptBASE64(sign)); }
     */



}
