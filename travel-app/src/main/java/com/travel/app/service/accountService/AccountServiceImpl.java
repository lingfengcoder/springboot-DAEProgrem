package com.travel.app.service.accountService;

import com.travel.app.mapper.user.AccountMapper;
import com.travel.app.entity.user.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;

    @Override
    public Account queryAccount(String accountId) {
        System.out.println(accountId);
        Account account= accountMapper.getAccount(accountId);
        return account;
    }

    @Override
    public boolean insertAccount(Account account) {
        return accountMapper.insetAccount(account);
    }

    @Override
    public boolean updateAccount(Account account) {
        return false;
    }

    @Override
    public boolean deleteAccount(String accountId) {
        return false;
    }
}
