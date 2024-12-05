package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account){
        if (account.getUsername() == null || account.getUsername().isBlank()){
            throw new IllegalArgumentException("Username cant be blank");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4){
            throw new IllegalArgumentException("Password needs to be more that 4 ch");
        }
        if (accountRepository.findByUsername(account.getUsername()).isPresent()){
            throw new IllegalStateException("Username already exists");
        }
        return accountRepository.save(account);
    }
    public Account login(String username, String password){
        return accountRepository.findByUsername(username).filter(account -> account.getPassword().equals(password))
        .orElseThrow(()-> new IllegalArgumentException("Invalid"));
    }
}
