package com.travel.app.mapper.user;


import com.travel.app.entity.user.User;
import org.apache.ibatis.annotations.*;

/**
 * 注解方式实现
 */
@Mapper//指定这是一个操作数据库的mapper
public interface UserMapper {

    @Select("select * from tb_user where user_id=#{userId}")
     User queryUserById(String userId)throws Exception;

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into tb_user(user_id,username,password,create_time,phone,email,is_vip,available)" +
            " values (#{userId},#{username},#{password},#{createTime},#{phone},#{email},0,1)")
     boolean insertUser(User user)throws Exception;

    @Update("update tb_user set password=#{password},phone=#{phone},email=#{email} where userId=#{userId}")
     boolean modifyUserById(User user)throws Exception;

    @Delete("update tb_user set available=0 where userId=#{userId}")
     boolean deleteUserById(String userId)throws Exception;

}
