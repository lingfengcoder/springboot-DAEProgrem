package com.travel.common.util.DAE.AES;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import org.bouncycastle.util.encoders.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AesUtil2 {

    private static final String encoding="utf-8";

    public static void main(String[] args) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        // TODO Auto-generated method stub

        String content="34534中文";
        String key="68977781a611722e";
        String iv="1234567890123456";
        String encrypted="";

        // System.out.println("3des加密后: "+(encrypted=encodeByAESFull(content, key, iv)));
        //System.out.println("3des解密后: "+decodeByAESFull(encrypted, key, iv));
        System.out.println("加密前："+content);
        System.out.println("加密后："+(encrypted=encodeByAES(content, key, iv)));
        encrypted="IZgZr2Q44127yhqmQ+biWQ==";
        System.out.println("解密后："+decodeByAES(encrypted, key, iv));
    }

    public static String encodeByAES(String content, String key, String iv){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());

            keyGenerator.init(128, random);


            SecretKey secretKey=keyGenerator.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec AESkey = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, AESkey, new IvParameterSpec(iv.getBytes("utf-8")));
            byte[] encryptData = cipher.doFinal(content.getBytes(encoding));
            return new String(Base64.encode(encryptData)).replaceAll(" ", "");
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    public static String decodeByAES(String content, String key, String iv){

        try{
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());

            keyGenerator.init(128, random);


            SecretKey secretKey=keyGenerator.generateKey();

            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec AESkey = new SecretKeySpec(enCodeFormat, "AES");

            Cipher cipher=Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, AESkey, new IvParameterSpec(iv.getBytes("utf-8")));
            byte[] decryptData = cipher.doFinal(Base64.decode(new String(content.getBytes("utf-8"))));
            return new String(decryptData,"utf-8");
        }catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("exception:"+e.toString());
        }
        return null;
    }



}
