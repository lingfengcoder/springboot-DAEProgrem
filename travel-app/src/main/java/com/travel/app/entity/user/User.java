package com.travel.app.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.common.annotation.regexAspect.RegexFilter;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
public class User {

    public final static String username_REGEX = "^[A-Za-z0-9_]{3,10}$";
    public final static String password_REGEX = "^[A-Za-z0-9_]{6,10}$";
    public final static String email_REGEX = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
    public final static String phone_REGEX = "^\\d{11}$";


    private Integer id;
    private String userId;
    @RegexFilter(regex = username_REGEX)
    private String username;
    @RegexFilter(regex = password_REGEX,isCanlog=false)
    private String password;

    @DateTimeFormat(pattern = "yyyy:mm:dd HH:MM:SS")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @RegexFilter(regex = phone_REGEX)
    private String phone;
    @RegexFilter(regex = email_REGEX)
    private String email;
    private Integer isVip;
    private Integer available;//是否是有效用户

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public Timestamp getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Timestamp createTime) {
//        this.createTime = createTime;
//    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }
}
