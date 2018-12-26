# springboot-DAEProgrem
一个注解解决前后台数据加密问题，通过结合AOP自定义注解 使用RSA+AES加密 

#使用案例
~~~
    @GetMapping("/queryUser")
    @DAEAnnotation
    public Object queryUserById(HttpServletRequest req){
        String userId=loginHandlerCenter.getUserOnLineInfo();
        if(userId==null)return super.FAIL("请先登录");
        String username=req.getParameter("username");
        System.out.println("data:"+username);
        User user= userService.queryUser(userId);
        return super.SUCCESS(user,200);
    }
~~~
