package com.ptit.contact.service;

import com.ptit.contact.converter.EmailConverter;
import com.ptit.contact.dto.EmailDTO;
import com.ptit.contact.entity.Email;
import com.ptit.contact.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    EmailConverter emailConverter;

    public EmailDTO saveEmail(Email email) {
        return emailConverter.toDTO(emailRepository.save(email));
    }

    public List<Email> getEmails() {
        return emailRepository.findAll();
    }

    public void deleteEmailById(int id) {
        emailRepository.deleteById(id);
    }

}
