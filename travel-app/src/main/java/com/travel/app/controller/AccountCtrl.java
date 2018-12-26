package com.travel.app.controller;

import com.travel.app.controller.base.BaseController;
import com.travel.app.entity.user.Account;

import com.travel.app.service.accountService.AccountService;
import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountCtrl extends HttpInterfaceImpl implements BaseController {


    @Autowired
    AccountService accountService;

    @ResponseBody
    @GetMapping("/queryAccount")
    public Object queryUserById(@RequestParam String accountId){

        Account account= accountService.queryAccount(accountId);
        return super.SUCCESS(account);
    }
}
