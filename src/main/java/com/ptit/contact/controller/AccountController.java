package com.ptit.contact.controller;

import com.ptit.contact.dto.AccountDTO;
import com.ptit.contact.entity.Account;
import com.ptit.contact.exception.NotFoundException;
import com.ptit.contact.service.AccountService;
import com.ptit.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    ContactService contactService;

    @PostMapping("/signup")
    public Account addAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PostMapping("/login")
    public AccountDTO login(@RequestBody Account account) {
        Account acc = accountService.getAccountByEmailPassword(account.getEmail(), account.getPassword());
        if(acc != null) {
            return acc.toDTO();
        }
        throw new NotFoundException("Sai tài khoản mật khẩu");
    }

}
