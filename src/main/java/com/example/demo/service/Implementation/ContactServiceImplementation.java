package com.example.demo.service.Implementation;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImplementation implements ContactService {

    private ContactRepository contactRepository;

    @Autowired
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
        if (contact.getName() == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (contact.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        if(contactRepository.findByEmail(contact.getEmail()) != null){
            throw new IllegalArgumentException("Email address must be unique");
        }
        return contactRepository.save(contact);
    }

    @Override
    public String deleteContact(Long id) {
        if(contactRepository.findById(id).isPresent()){
            contactRepository.deleteById(id);
            return "Contact deleted successfully";
        }
            return "Contact not exist";
        }
}
