package com.travel.app.service.RegisterService;

import com.travel.app.entity.user.User;

/**
 * @Auther: wz
 * @Date: 2018/12/21 11:42
 * @Description:
 */
public interface RegisterService {
    User register(User user);
    boolean checkUserExist(User user);
}
