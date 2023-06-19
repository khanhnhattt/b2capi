package com.example.demo.service;

import com.example.demo.model.Account;

import java.util.List;

public interface IAccountService {
    void createAccount(Account account);
    void deleteAccount(int id);
    void updateAccount(int id, Account newAccount);
    Account getAccount(int id);

    List<Account> getListAccount();
}
