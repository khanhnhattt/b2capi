package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/")
    public List<Account> getListAccount()
    {
        return accountService.getListAccount();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable int id)
    {
        return accountService.getAccount(id);
    }

    @PostMapping("/")
    public String addAccount(@RequestBody Account account)
    {
        accountService.createAccount(account);
        return "Account added successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable int id)
    {
        accountService.deleteAccount(id);
        return "Account deleted!";
    }

    @PutMapping("/")
    public String updateAccount(@RequestBody Account account)
    {
        accountService.updateAccount(account.getId(), account);
        return "Account updated!";
    }

}
