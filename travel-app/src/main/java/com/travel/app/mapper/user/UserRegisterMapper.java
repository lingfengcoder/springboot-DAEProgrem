package com.travel.app.mapper.user;

import com.travel.app.entity.user.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: wz
 * @Date: 2018/12/21 11:46
 * @Description:
 */
@Mapper
public interface UserRegisterMapper {
    boolean register(User user)throws Exception;
    List<User> checkUserExist(User user)throws Exception;
}
