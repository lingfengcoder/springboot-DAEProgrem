package com.travel.app.service.RegisterService;

import com.travel.app.entity.user.User;
import com.travel.app.mapper.user.UserRegisterMapper;
import com.travel.common.exception.ToC_Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Auther: wz
 * @Date: 2018/12/21 11:43
 * @Description:
 */
@Service
@Transactional
public class RegisterImpl implements RegisterService{

    @Resource
    UserRegisterMapper userRegisterMapper;

    @Override
    public User register(User user) {
        if(checkUserExist(user))return null;
        try {
            if(!userRegisterMapper.register(user)) return null;
        } catch (Exception e) {
            throw new ToC_Exception("注册失败");
        }
        user.setUserId("");
        user.setPassword("");
        user.setIsVip(0);
        user.setAvailable(1);
        return user;
    }

    /**
     * @Description 检查用户是否存在 false 不存在
     * @param: User
     * @return: boolean
     * @auther: wz
     * @date: 2018/12/21 12:05
     */
    @Override
    public boolean checkUserExist(User user) {
        try {
            List<User> list = userRegisterMapper.checkUserExist(user);
            if(list==null||list.size()==0)return false;
        } catch (Exception e) {
            throw new ToC_Exception("查找用户失败");
        }
        return true;
    }
}
