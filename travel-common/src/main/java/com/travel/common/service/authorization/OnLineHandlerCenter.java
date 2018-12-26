package com.travel.common.service.authorization;

import com.travel.common.util.CookieUtil;
import com.travel.common.util.DAEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Auther: wz
 * @Date: 2018/12/20 17:47
 * @Description:
 */
@Component
public class OnLineHandlerCenter extends AuthorizeHandler {

    @Autowired
    protected DAEUtil daeUtil;

    public void setOnLineStatus(String msg){
        setEncryptOnlineStatus(daeUtil.encryptCookieInfo(msg));
    }
    public String getUserOnLineInfo(){
        return daeUtil.decryptCookieInfo(getUserOnlineEncryptInfo());
    }
    public boolean checkOnLineStatus(){
        String status=getUserOnlineEncryptInfo();
        if(status==null)return false;
        return daeUtil.decryptCookieInfo(status)!=null;
    }
    private String getUserOnlineEncryptInfo(){
        return CookieUtil.getCookieValue(COOKIE_ON_LINE);
    }
    private void setEncryptOnlineStatus(String encryptStr){
        CookieUtil.setCookie(COOKIE_ON_LINE,encryptStr);
    }

    public void setOffLineStatus(){
        CookieUtil.removeCookie(COOKIE_ON_LINE);
    }


}
