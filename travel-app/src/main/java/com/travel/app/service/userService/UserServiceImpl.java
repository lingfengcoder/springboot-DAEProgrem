package com.travel.app.service.userService;


import com.travel.app.mapper.user.UserMapper;
import com.travel.app.entity.user.User;
import com.travel.common.exception.ToC_Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public User queryUser(String userId) {
        User u ;
        try {
            u = userMapper.queryUserById(userId);
            u.setPassword("");
            u.setUserId("");
        } catch (Exception e) {
            throw new ToC_Exception("查询用户异常");
        }
        return u;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userMapper.modifyUserById(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteUser(String userId) {
        try {
            return userMapper.deleteUserById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
