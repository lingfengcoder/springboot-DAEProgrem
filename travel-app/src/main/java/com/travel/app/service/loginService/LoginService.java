package com.travel.app.service.loginService;

import com.travel.app.DTO.user.UserLoginDTO;
import com.travel.app.entity.user.User;

/**
 * @Auther: wz
 * @Date: 2018/12/20 14:31
 * @Description:
 */
public interface LoginService {

    User login(UserLoginDTO u);
}
