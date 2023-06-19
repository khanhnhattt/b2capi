package com.example.demo.service.impl;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    public void createAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void updateAccount(int id, Account newAccount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not exist"));

        account.setUsername(newAccount.getUsername());
        account.setPassword(newAccount.getPassword());

        accountRepository.save(account);
    }

    @Override
    public Account getAccount(int id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public List<Account> getListAccount() {
        return accountRepository.findAll();
    }
}
