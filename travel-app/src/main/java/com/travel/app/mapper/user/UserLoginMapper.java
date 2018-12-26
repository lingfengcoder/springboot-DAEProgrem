package com.travel.app.mapper.user;

import com.travel.app.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: wz
 * @Date: 2018/12/19 12:54
 * @Description:
 */
@Mapper
public interface UserLoginMapper {

    User userLogin(String username, String phone)throws Exception;

}
