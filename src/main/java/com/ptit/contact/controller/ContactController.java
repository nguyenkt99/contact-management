package com.ptit.contact.controller;

import com.ptit.contact.converter.ContactConverter;
import com.ptit.contact.dto.AccountDTO;
import com.ptit.contact.dto.ContactDTO;
import com.ptit.contact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactConverter contactConverter;

    @GetMapping("/accounts/{id}")
    public List<ContactDTO> findContacts(@PathVariable int id) {
        return contactService.getContactsByAccountId(id);
    }

    @GetMapping("/{id}")
    public ContactDTO findContact(@PathVariable int id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    public ContactDTO addContact(@RequestBody ContactDTO contactDTO) {
        return contactService.saveContact(contactDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteContactById(@PathVariable int id) {
        contactService.deleteContactById(id);
    }

    @PutMapping("/{id}")
    public ContactDTO updateContact(@RequestBody ContactDTO contactDTO, @PathVariable int id) {
        contactDTO.setId(id);
        return contactService.updateContact(contactDTO);
    }
}
