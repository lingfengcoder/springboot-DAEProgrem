package com.travel.app.controller;

import com.travel.app.controller.base.BaseController;
import com.travel.app.entity.user.User;
import com.travel.app.service.RegisterService.RegisterService;
import com.travel.common.annotation.DAEAspect.DAEAnnotation;
import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import com.travel.common.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @Auther: wz
 * @Date: 2018/12/21 11:25
 * @Description:
 */
@RestController
public class RegisterCtrl extends HttpInterfaceImpl implements BaseController {

    @Autowired
    RegisterService registerService;

    @PostMapping("/registerUser")
    @DAEAnnotation
    public Object registerUser(HttpServletRequest req) {
        User user = new User();
        user.setUsername(req.getParameter("username"));
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        String password = req.getParameter("password");
        user.setPassword(password);
        user.setUserId(RandomUtil.getUUID());
        user.setCreateTime(new Date());

        String result = regexUtil.regexFilter_String(user);
        if (!StringUtils.isEmpty(result))
            return super.FAIL(result);

        password = super.daeUtil.encryptLoginPassword(password);
        user.setPassword(password);

        user = registerService.register(user);
        if (user == null) return super.FAIL("用户名或手机号已注册");
        return super.SUCCESS(user, "注册成功");
    }

}
