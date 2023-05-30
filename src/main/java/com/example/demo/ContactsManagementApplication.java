package com.example.demo;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ContactsManagementApplication {

	private final ContactRepository contactRepository;

	public ContactsManagementApplication(ContactRepository contactRepository) {
		this.contactRepository = contactRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(ContactsManagementApplication.class, args);


	}
//	@PostConstruct
//	public void run() {
//
//		Contact contact1 = new Contact("Frankie Dejong", "frankie12@gmail.com");
//		Contact contact2 = new Contact("Robert Lewandowski", "Lewyy2@example.com");
//
//		contactRepository.save(contact1);
//		contactRepository.save(contact2);
//
//		List<Contact> contacts = contactRepository.findAll();
//		for (Contact contact : contacts) {
//			System.out.println(contact);
//		}
//	}
	}
