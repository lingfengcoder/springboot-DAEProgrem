# springboot-DAEProgrem
一个注解解决前后台数据加密问题，通过结合AOP自定义注解 使用RSA+AES加密 

### 加密接口DEMO
  诠释：对于很多应用有时候可能没有https或者即使使用https但是加密要求更高，于是需要对传输的数据进行加密，但是加密代码写起来不仅冗长而且很繁琐，于是也就花费了一些时间做了这个注解。原理：主要使用流行的RSA非对称加密技术和AES对称加密技术进行混合加密，RSA公钥提供给客户端，私钥服务器保存。客户端发起请求时，使用AES生成对应一对key-iv密钥，对数据进行加密，随后使用RSA公钥将AES的密钥进行加密，并作为参数一起发送给服务端，同时。服务端接收到数据后，进行一个逆过程，由于RSA是非对称加密，使用RSA公钥将数据加密后，只有RSA对应的私钥才能正确解密，这也就保证了AES密钥的保密性，将当然该注解核心功能
~~~java
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
### 可输入数据正则过滤
~~~java
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
    public Object modifyUser(User user){
        
        //此处返回 没有通过正则过滤的字段信息通过string返回给客户端
        //regexUtil 中也包含返回map形式的未通过字段的方法
        //          也包含只返回boolean通过与否的方法
        String result=regexUtil.regexFilter_String(user);//传入要过滤的封装数据对象
        if(!StringUtils.isEmpty(result))
            return super.FAIL(result);
        ...
    }
~~~
### 对象数据转换工具（拷贝）PO-VO 对象-MAP MAP-对象
利用JAVA反射可将任意拥有相同属性名切属性类型也相同的两个对象进行数据拷贝，例如：可将数据库查出的po对象与controller中传输的数据对象进行转换，可以配合上面的“可输入数据正则过滤”的注解使用节约更多代码
~~~java
 @Override
    public boolean updateUser(UserVo user) {
        User tarUser = new User();
        copyObject(user, tarUser);//进行数据拷贝
        return userMapper.updateByPrimaryKeySelective(tarUser) > 0;
    }
~~~


