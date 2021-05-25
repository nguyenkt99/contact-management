package com.ptit.contact.service;


import com.ptit.contact.converter.PhoneConverter;
import com.ptit.contact.dto.PhoneDTO;
import com.ptit.contact.entity.Phone;
import com.ptit.contact.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PhoneConverter phoneConverter;

    public PhoneDTO savePhone(Phone phone) {
        return phoneConverter.toDTO(phoneRepository.save(phone));
    }

    public List<Phone> getPhones() {
        return phoneRepository.findAll();
    }

    public void deletePhoneById(int id) {
        phoneRepository.deleteById(id);
    }
}
