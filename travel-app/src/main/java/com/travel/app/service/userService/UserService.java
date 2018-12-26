package com.travel.app.service.userService;

import com.travel.app.entity.user.User;

public interface UserService {

    User queryUser(String userId);
    //boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(String userId);

}
