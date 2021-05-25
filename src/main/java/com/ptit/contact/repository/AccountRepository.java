package com.ptit.contact.repository;

import com.ptit.contact.entity.Account;
import com.ptit.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

//    @Query("SELECT a FROM Account a WHERE a.email = ?1")
//    Account findOne(String email);

    @Query("SELECT c FROM Contact c WHERE c.account.email = ?1")
    List<Contact> findAllContactsByEmail(String email);

    @Query("SELECT a FROM Account a WHERE a.email = ?1 AND a.password = ?2")
    Account findOne(String email, String password);
}
