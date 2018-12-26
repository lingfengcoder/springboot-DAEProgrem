package com.travel.app.controller;


import com.travel.app.DTO.user.UserLoginDTO;
import com.travel.app.controller.base.BaseController;
import com.travel.app.entity.user.User;
import com.travel.app.service.loginService.LoginServiceImpl;
import com.travel.common.exception.ToC_Exception;
import com.travel.common.service.authorization.OnLineHandlerCenter;
import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import com.travel.common.util.DAEUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


import static com.travel.common.superHttpCenter.controller.HttpCodeStateCenter.NOREGISTER;
import static com.travel.common.superHttpCenter.controller.HttpCodeStateCenter.WRONG_USERINFO;


@RestController
public class LoginCtrl extends HttpInterfaceImpl implements BaseController {

    @Autowired
    LoginServiceImpl loginService;

    @Autowired
    OnLineHandlerCenter onLineHandlerCenter;

    @Autowired
    DAEUtil daeUtil;

    @PostMapping("/appLogin")
    //@DAEAnnotation
    public Object login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            String excpeptionInfo = "缺少用户信息" + Math.random();
            logger.trace(excpeptionInfo);
            throw new ToC_Exception(excpeptionInfo);
        }
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(username);
       // password = super.daeUtil.encryptLoginPassword(password);
        userLoginDTO.setPassword(password);

        //regex过滤
        String result=regexUtil.regexFilter_String(userLoginDTO);
        if(!StringUtils.isEmpty(result))
            return super.FAIL(result);

        password = super.daeUtil.encryptLoginPassword(password);
        userLoginDTO.setPassword(password);

        User user = loginService.login(userLoginDTO);
        if (userLoginDTO == null) {
            return super.FAIL("用户不存在", NOREGISTER);
        } else if (user.getUserId() == null) {
            return super.FAIL("用户名或密码错误", WRONG_USERINFO);
        }
        //设置登录状态
        onLineHandlerCenter.setOnLineStatus(user.getUserId());
        return super.SUCCESS(user, "登录成功");
    }

    @RequestMapping("/appLogout")
    public Object logout(HttpServletRequest request) {
        if (onLineHandlerCenter.getUserOnLineInfo() == null)
            return super.FAIL("请先登录");
        onLineHandlerCenter.setOffLineStatus();
        return super.SUCCESS("成功退出");
    }


}
