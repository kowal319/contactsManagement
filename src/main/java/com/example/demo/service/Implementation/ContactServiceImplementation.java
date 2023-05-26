package com.example.demo.service.Implementation;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImplementation implements ContactService {

    private ContactRepository contactRepository;

    public ContactServiceImplementation(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public List<Contact> findAllContacts() {
        List<Contact> contacts = contactRepository.findAll();
        return contacts;
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }


    @Override
    public String deleteContact(Long id) {
        if(contactRepository.findById(id).isPresent()){
            contactRepository.deleteById(id);
            return "Contact deleted sucessfuly";
        }
            return "Contact not exist";
        }
}
