package com.travel.common.superHttpCenter.controller;

/**
 * 状态中心
 */
public abstract class HttpCodeStateCenter {
    //登录注册
    public final static int SUCCESS=200;//通过
    public final static int WRONG_USERINFO=201;//用户名密码错误
    public final static int NEED_LOGIN=202;//需要登录
    public final static int RELOGIN=203;//需要重新登录
    public final static int NOREGISTER=204;//没注册

    public final static int FAIL_400=400;//收到请求不予处理
    public final static int FAIL_404=404;//找不到资源
    //权限
    public final static int NO_POWER=403;//没权限

    //错误
    public final static int ERROR=110;//接口错误
    public final static int SER_ERROR=510;//服务器内部错误
    public final static int OTHER_ERROR=100;//其他错误


}
