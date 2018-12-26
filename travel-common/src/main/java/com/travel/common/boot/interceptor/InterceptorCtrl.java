package com.travel.common.boot.interceptor;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InterceptorCtrl extends InterceptorInterface {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        try{
            InterceptorInterface InterceptorInterface=new LoginInterceptor();
            return InterceptorInterface.preHandle(request,response,handler);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 登录拦截
     */
    class LoginInterceptor extends  InterceptorInterface{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

            Cookie[] cookieArr=request.getCookies();

            if(cookieArr==null||cookieArr.length==0){

                Cookie coo=new Cookie("_test","_wz");
                coo.setDomain(".51wz");
                coo.setPath("/");
            }else{
                super.logger.trace(cookieArr.toString());
            }
            super.logger.trace("IP:"+request.getRemoteAddr());
            super.logger.trace("LoginInterceptor+++++++++++++++");
            return true;
        }
    }
}
