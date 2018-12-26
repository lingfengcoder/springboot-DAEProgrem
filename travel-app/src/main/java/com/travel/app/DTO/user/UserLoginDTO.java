package com.travel.app.DTO.user;

import com.travel.common.annotation.regexAspect.RegexFilter;
/**
 * @Auther: wz
 * @Date: 2018/12/25 11:10
 * @Description:
 */
public class UserLoginDTO {

    public final static String username_REGEX = "^([A-Za-z0-9]{3,10})|(\\d{11})$";
    public final static String password_REGEX = "^[A-Za-z0-9_]{6,10}$";

    @RegexFilter(regex = username_REGEX,necessary = true)
    private String username;
    @RegexFilter(regex = password_REGEX,necessary = true,isCanlog=false)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
