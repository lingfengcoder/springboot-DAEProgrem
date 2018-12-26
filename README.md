# springboot-DAEProgrem
一个注解解决前后台数据加密问题，通过结合AOP自定义注解 使用RSA+AES加密 

##加密接口DEMO
~~~
    @GetMapping("/queryUser")
    @DAEAnnotation//使用注解对接口增强
    public Object queryUserById(HttpServletRequest req){
        //获取解密密后的参数数据
        String username=req.getParameter("username");
        System.out.println("data:"+username);
        User user= userService.queryUser(userId);
        return super.SUCCESS(user,200);
    }
~~~
##可输入数据正则过滤
~~~
   //1.vo 定义
    @RegexFilter(regex = username_REGEX)
    private String username;
    @RegexFilter(regex = password_REGEX,isCanlog=false)
    private String password;
    @RegexFilter(regex = phone_REGEX)
    private String phone;
    @RegexFilter(regex = email_REGEX)
    private String email;
    
    //2.controller中使用
     @PostMapping("/modifyUser")
    public Object modifyUser(HttpServletRequest req){
        ...
        User user=new User();
        user.setUserId(userId);
        user.setEmail(req.getParameter("email"));
        user.setPhone(req.getParameter("phone"));
        user.setPassword(req.getParameter("password"));
        //此处返回 没有通过正则过滤的字段信息通过string返回给客户端
        //regexUtil 中也包含返回map形式的未通过字段的方法
        //          也包含只返回boolean通过与否的方法
        String result=regexUtil.regexFilter_String(user);//传入要过滤的封装数据对象
        if(!StringUtils.isEmpty(result))
            return super.FAIL(result);
        ...
    }
~~~


