package com.travel.common.boot.interceptor;

import com.alibaba.fastjson.JSON;
import com.travel.common.service.authorization.OnLineHandlerCenter;
import com.travel.common.superHttpCenter.controller.httpResponseBean.Http400;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.travel.common.superHttpCenter.controller.HttpCodeStateCenter.FAIL_400;

/**
 * @Auther: wz
 * @Date: 2018/12/20 18:07
 * @Description: 登录拦截
 */
@Component
public class OnLineInterceptorCtrl extends InterceptorInterface implements HandlerInterceptor {

    @Resource
    OnLineHandlerCenter onLineHandlerCenter;

   public  OnLineInterceptorCtrl(){
        //onLineHandlerCenter=new OnLineHandlerCenter();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       if(onLineHandlerCenter.checkOnLineStatus())return true;
        PrintWriter pw= null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(FAIL_400);
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(pw!=null){
                pw.print(JSON.toJSON(new Http400(400,"请先登录")));
                pw.flush();
                pw.close();
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("OnLineInterceptorCtrl-->postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("OnLineInterceptorCtrl-->afterCompletion");

    }


}
