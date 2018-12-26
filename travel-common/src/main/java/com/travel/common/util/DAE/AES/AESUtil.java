package com.travel.common.util.DAE.AES;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Map;

@Component
public class AESUtil {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";
    public static final String ALGORITHM = "AES/CBC/PKCS7Padding";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args){

        String content="asd中文666asd";

        content="{\"userId\":222,\"arr\":[1,\"op\",2,3,4,\"fg\"],\"username\":\"张三\",\"password\":\"123456\",\"createTime\":\"2018-12-01 12:12:01\",\"phone\":\"18845121260\",\"email\":\"2650014538@qq.com\",\"isVip\":1,\"available\":1}";

        Map map0 = JSON.parseObject(content, Map.class);
        System.out.println("map0:"+map0);
        JSONArray obj0=(JSONArray) map0.get("arr");
        ArrayList list= JSONArray.toJavaObject(obj0,ArrayList.class);
        System.out.println(list+" "+list.getClass());
        JSONObject job=JSON.parseObject(content);
        System.out.println(job);
        Object obj_json=job.get("arr");

        System.out.println(obj_json+"==="+obj_json.getClass());
        Map<String, Object> map = JSONObject.toJavaObject(job, Map.class);
        System.out.println("map"+map.toString());

        Object obj=map.get("createTime");
        System.out.println(obj+" "+obj.getClass());

        String key="45859b5830bcdd52aeb97e9f589daa52";
        String iv="366aaa1b5b4321df";
        String encrypted="";

        System.out.println("加密前："+content);
        System.out.println("加密后："+(encrypted=encrypt(content, key,iv)));
        //encrypted="2tqDEDAm3OZoBughwAoOqA==";
        System.out.println("解密后："+decrypt(encrypted, key,iv));
    }

    /**
     * 加密
     *
     * @param content
     * @param key
     * @return
     */
    public static String encrypt( String content,  String key,String iv) {
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec =null;
            if(iv==null||iv.equals("")){
                paramSpec=new IvParameterSpec(new byte[16]);
            }else{
                paramSpec =new IvParameterSpec(iv.getBytes(CHARSET_NAME));
            }
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
            result = cipher.doFinal(content.getBytes(CHARSET_NAME));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
            //Throwables.propagate(ex);
        }
        return Base64.encodeBase64String(result);
    }

    /**
     * 解密
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt( String content,  String key,String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(CHARSET_NAME), AES_NAME);

            AlgorithmParameterSpec paramSpec =null;
            if(iv==null||iv.equals("")){
                paramSpec=new IvParameterSpec(new byte[16]);
            }else{
                paramSpec =new IvParameterSpec(iv.getBytes(CHARSET_NAME));
            }

            cipher.init(Cipher.DECRYPT_MODE, keySpec, paramSpec);
            return new String(cipher.doFinal(Base64.decodeBase64(content)), CHARSET_NAME);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
