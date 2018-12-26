package com.travel.app.controller;

import com.travel.app.controller.base.OnLineBaseController;
import com.travel.app.service.userService.UserService;
import com.travel.common.annotation.DAEAspect.DAEAnnotation;
import com.travel.app.entity.user.User;
import com.travel.common.service.authorization.OnLineHandlerCenter;
import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import com.travel.common.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserCtrl extends HttpInterfaceImpl implements OnLineBaseController {

    @Autowired
    UserService userService;
    @Autowired
    OnLineHandlerCenter loginHandlerCenter;

    @GetMapping("/queryUser")
    @DAEAnnotation
    public Object queryUserById(HttpServletRequest req){
        String userId=loginHandlerCenter.getUserOnLineInfo();
        if(userId==null)return super.FAIL("请先登录");
        //String userId=req.getParameter("userId");
        //System.out.println("data:"+userId);
        User user= userService.queryUser(userId);
        return super.SUCCESS(user,200);
    }

    @PostMapping("/modifyUser")
    public Object modifyUser(HttpServletRequest req){
        String userId=loginHandlerCenter.getUserOnLineInfo();
        if(userId==null)return super.FAIL("请先登录");
        User user=new User();
        user.setUserId(userId);
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        user.setPassword(req.getParameter("password"));

        String result=regexUtil.regexFilter_String(user);
        if(!StringUtils.isEmpty(result))
            return super.FAIL(result);
        user.setPassword(super.daeUtil.encryptLoginPassword(user.getPassword()));
        return null;
    }

    @ResponseBody
    @PostMapping("/testPost")
    //@DAEAnnotation(needEncrypt = true)
    public Object testPost(HttpServletRequest req){

        String userId=req.getParameter("username");
        System.out.println("data:"+userId);
        User user= new User();
        Map map=new HashMap<>();
        map.put("name","wz");
        map.put("age",24);
        map.put("user",user);
        String msg="this is a good message";
        return super.SUCCESS(map,msg,200);
    }


}
