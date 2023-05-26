package com.example.demo.service;

import com.example.demo.entity.Contact;

import java.util.List;


public interface ContactService {
    List<Contact> findAllContacts();
    Contact createContact(Contact contact);
    String deleteContact(Long id);

}
