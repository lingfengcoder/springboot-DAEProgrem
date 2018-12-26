package com.travel.common.superHttpCenter.DAEService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.travel.common.superHttpCenter.controller.httpBean.RequestDecryptBean;
import com.travel.common.superHttpCenter.controller.httpBean.RequestEncryptBean;
import com.travel.common.superHttpCenter.controller.httpBean.ResponseDecryptBean;
import com.travel.common.superHttpCenter.controller.httpBean.ResponseEncryptBean;
import com.travel.common.util.DAE.AES.AESUtil;
import com.travel.common.util.DAE.MD5.MD5Util;
import com.travel.common.util.DAE.RSA.RSAUtil;
import com.travel.common.util.DAE.bean.AESBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.travel.common.util.TimeUtil.compareTimeWithThreshold;

@Service
public class DAEServiceImpl implements DAEServiceInterface<RequestEncryptBean, AESBean, RequestDecryptBean, ResponseEncryptBean> {

    @Autowired
    RSAUtil RSAUtil;


    /**
     * 时间戳检查
     *
     * @param requestEncryptBean
     * @param aesBean
     * @return
     */
    @Override
    public boolean checkTimeStamp(RequestEncryptBean requestEncryptBean, AESBean aesBean) {
        String timeStamp = requestEncryptBean.getTimeStamp();
        timeStamp = AESUtil.decrypt(timeStamp, aesBean.getKey(), aesBean.getIv());
        return compareTimeWithThreshold(new Long(timeStamp), new Date());
    }

    @Override
    public boolean checkSignature(RequestEncryptBean requestEncryptBean, AESBean aesBean) {
        StringBuffer sbff = new StringBuffer();
        sbff.append(requestEncryptBean.getTimeStamp());
        sbff.append(requestEncryptBean.getData());
        sbff.append(requestEncryptBean.getAesBeanStr());
        if (requestEncryptBean.getSignature().equals(MD5Util.encryptByMd5(sbff.toString().toLowerCase()))) return true;
        return false;
    }

    @Override
    public AESBean decryptKey(RequestEncryptBean requestEncryptBean) throws Exception {
        String aesBeanStr = requestEncryptBean.getAesBeanStr();
        aesBeanStr = RSAUtil.decryptByDefaultPrivateKey(aesBeanStr);
        JSONObject jsonObject = JSON.parseObject(aesBeanStr);
        AESBean aesBean = new AESBean();
        aesBean.setKey(jsonObject.getString("key"));
        aesBean.setIv(jsonObject.getString("iv"));
        return aesBean;
    }

    @Override
    public RequestDecryptBean decryptData(RequestEncryptBean requestEncryptBean) {
        RequestDecryptBean requestDecryptBean = null;
        try {
            AESBean aesBean = decryptKey(requestEncryptBean);
            boolean f1 = checkTimeStamp(requestEncryptBean, aesBean);
            if (!f1) return null;
            boolean f2 = checkSignature(requestEncryptBean, aesBean);
            if (!f2) return null;

            requestDecryptBean = new RequestDecryptBean();
            requestDecryptBean.setAesBean(aesBean);
            String data = AESUtil.decrypt(requestEncryptBean.getData(), aesBean.getKey(), aesBean.getIv());
            requestDecryptBean.setData(JSON.parseObject(data));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return requestDecryptBean;
    }

    @Override
    public ResponseEncryptBean encryptData(AESBean aesBean, Object... srcRET) throws Exception {
        ResponseEncryptBean responseEncryptBean = new ResponseEncryptBean();
        String code = null;
        String data = null;
        String msg = null;
        for (Object itemRET : srcRET) {
            if (itemRET.getClass().equals(ResponseDecryptBean.class)) {
                ResponseDecryptBean rb = (ResponseDecryptBean) itemRET;
                code = AESUtil.encrypt(rb.getCode() + "", aesBean.getKey(), aesBean.getIv());
                data = AESUtil.encrypt(JSONObject.toJSONString(rb.getData(), SerializerFeature.WriteMapNullValue),
                        aesBean.getKey(), aesBean.getIv());
                msg = AESUtil.encrypt(rb.getMessage(), aesBean.getKey(), aesBean.getIv());
            }
        }

        responseEncryptBean.setCode(code);
        responseEncryptBean.setData(data);
        responseEncryptBean.setMessage(msg);

        return responseEncryptBean;
    }
}
