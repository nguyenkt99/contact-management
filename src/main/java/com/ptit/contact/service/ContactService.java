package com.ptit.contact.service;

import com.ptit.contact.converter.EmailConverter;
import com.ptit.contact.converter.PhoneConverter;
import com.ptit.contact.converter.ContactConverter;
import com.ptit.contact.dto.EmailDTO;
import com.ptit.contact.dto.PhoneDTO;
import com.ptit.contact.dto.ContactDTO;
import com.ptit.contact.entity.Email;
import com.ptit.contact.entity.Phone;
import com.ptit.contact.entity.Contact;
import com.ptit.contact.imageuploader.ImageUploader;
import com.ptit.contact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired AccountService accountService;

    @Autowired
    ImageUploader imageUploader;

    @Autowired
    private ContactConverter contactConverter;

    @Autowired
    private PhoneConverter phoneConverter;

    @Autowired
    private EmailConverter emailConverter;

    public List<ContactDTO> getContactsByAccountId(int id) {
        List<ContactDTO> contactDTOS = new ArrayList<>();
        List<Contact> contacts = contactRepository.findAllByAccountId(id);
        for(Contact contact : contacts) {
            contactDTOS.add(contactConverter.toDTO(contact));
        }
        return contactDTOS;
    }

    public ContactDTO getContactById(int id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if(contact != null)
            return contactConverter.toDTO(contact);
        return null;
    }

    public ContactDTO saveContact(ContactDTO contactDTO) {
        String url = imageUploader.uploadImage(contactDTO.getPhoto());
        if(url != null)
            contactDTO.setPhoto(url);
        Contact contact = contactConverter.toContact(contactDTO);
        contact.setAccount(accountService.getAccountById(contactDTO.getAccountId()));
        return contactConverter.toDTO(contactRepository.save(contact));
    }

    public ContactDTO updateContact(ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(contactDTO.getId()).orElse(null);
        contact.setName(contactDTO.getName());
        contact.setAddress(contactDTO.getAddress());
        contact.setPhoto(contactDTO.getPhoto());
        
        List<PhoneDTO> phoneDTOs = contactDTO.getPhones();
        List<EmailDTO> emailDTOs = contactDTO.getEmails();
        List<Phone> phones = contact.getPhones();
        List<Email> emails = contact.getEmails();

        // set phones
        for(int i = 0; i < phones.size(); i++) {
            Phone phone = phones.get(i);
            boolean isExists = false;
            for(int j = 0; j < phoneDTOs.size(); j++) {
                PhoneDTO phoneDTO = phoneDTOs.get(j);
                if(phoneDTO.getId() != null) {
                    if(phone.getId().intValue() == phoneDTO.getId().intValue()) {
                        isExists = true;
                        phone.setPhoneNumber(phoneDTO.getPhoneNumber());
                        phone.setType(phoneDTO.getType());
                        phone.setContact(contact);
                        break;
                    }
                }
            }

            if(!isExists) {
                phones.remove(i);
            }
        }

        for(int i = 0; i < phoneDTOs.size(); i++) {
            if(phoneDTOs.get(i).getId() == null) {
                Phone phone = phoneConverter.toPhone(phoneDTOs.get(i));
                phone.setContact(contact);
                phones.add(phone);
            }
        }

        // set emails
        for(int i = 0; i < emails.size() && emails != null; i++) {
            Email email = emails.get(i);
            boolean isExists = false;
            for(int j = 0; emailDTOs != null && j < emailDTOs.size(); j++) {
                EmailDTO emailDTO = emailDTOs.get(j);
                if(emailDTO.getId() != null) {
                    if(email.getId().intValue() == emailDTO.getId().intValue()) {
                        isExists = true;
                        email.setEmailAddress(emailDTO.getEmailAddress());
                        email.setType(emailDTO.getType());
                        email.setContact(contact);
                        break;
                    }
                }
            }

            if(!isExists) {
                emails.remove(i);
            }
        }

        for(int i = 0; emailDTOs != null && i < emailDTOs.size(); i++) {
            if(emailDTOs.get(i).getId() == null) {
                Email email = emailConverter.toEmail(emailDTOs.get(i));
                email.setContact(contact);
                emails.add(email);
            }
        }

        contact.setPhones(phones);
        contact.setEmails(emails);
        return contactConverter.toDTO(contactRepository.save(contact));
    }

    public void deleteContactById(int id) {
        contactRepository.deleteById(id);
    }
}
