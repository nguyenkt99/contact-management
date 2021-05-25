package com.ptit.contact.controller;

import com.ptit.contact.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/phones/")
public class PhoneController {
    @Autowired
    PhoneService phoneService;

    @DeleteMapping("/{id}")
    public void deletePhoneById(@PathVariable int id) {
        phoneService.deletePhoneById(id);
    }
}
