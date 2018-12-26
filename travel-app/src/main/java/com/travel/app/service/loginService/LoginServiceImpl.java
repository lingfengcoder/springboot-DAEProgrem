package com.travel.app.service.loginService;

import com.travel.app.DTO.user.UserLoginDTO;
import com.travel.app.entity.user.User;
import com.travel.app.mapper.user.UserLoginMapper;
import com.travel.common.exception.ToC_Exception;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Auther: wz
 * @Date: 2018/12/20 14:32
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserLoginMapper userLoginMapper;

    @Override
    public User login(UserLoginDTO u) {
        try {
            User user = userLoginMapper.userLogin(u.getUsername(),u.getUsername());
            if (user == null) return null;
            String tempStr = user.getPassword();
            //用户名密码错误
            if (StringUtils.isEmpty(tempStr) || !u.getPassword().equals(tempStr)) return user;
            user.setPassword("");
            return user;
        } catch (Exception e) {
            throw new ToC_Exception("数据库操作错误", e);
        }
    }

}
