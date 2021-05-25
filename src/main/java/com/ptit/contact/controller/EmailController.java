package com.ptit.contact.controller;

import com.ptit.contact.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/emails/")
public class EmailController {
    @Autowired
    EmailService emailService;

    @DeleteMapping("/{id}")
    public void deleteEmailById(@PathVariable int id) {
        emailService.deleteEmailById(id);
    }
}
