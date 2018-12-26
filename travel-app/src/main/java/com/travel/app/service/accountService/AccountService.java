package com.travel.app.service.accountService;

import com.travel.app.entity.user.Account;
public interface AccountService {

    Account queryAccount(String accountId);
    boolean insertAccount(Account account);
    boolean updateAccount(Account account);
    boolean deleteAccount(String accountId);
}
