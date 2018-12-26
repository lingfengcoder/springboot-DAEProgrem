package com.travel.app.mapper.user;

import com.travel.app.entity.user.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * xml 文件方式实现
 */
@Mapper
public interface AccountMapper {

     Account getAccount(String accountId);

     boolean insetAccount(Account account);
}
