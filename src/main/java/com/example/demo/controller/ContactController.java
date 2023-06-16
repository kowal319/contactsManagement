package com.example.demo.controller;


import com.example.demo.entity.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping()
    public List<Contact> getAllContacts(){
        return contactService.findAllContacts();
    }

    @PostMapping()
    public Contact saveContact(@RequestBody Contact contact){
        return contactService.createContact(contact);
    }


    @DeleteMapping("/{id}")
    public String deleteContact(@PathVariable("id") Long id){
        return contactService.deleteContact(id);
    }
}
