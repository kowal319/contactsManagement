package com.example.demo;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import com.example.demo.service.Implementation.ContactServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class ContactsManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	private ContactService contactService;
	private ContactRepository contactRepository;

	@BeforeEach
	public void setUp() {
		contactRepository = Mockito.mock(ContactRepository.class);
		contactService = new ContactServiceImplementation(contactRepository);
	}

	@Test
	public void testFindAllContacts() {
		// Given
		List<Contact> expectedContacts = new ArrayList<>();
		expectedContacts.add(new Contact("Leo Messi", "messi@barca.com"));
		expectedContacts.add(new Contact("Erling Halland", "erling.halland@city.com"));
		when(contactRepository.findAll()).thenReturn(expectedContacts);

		// When
		List<Contact> actualContacts = contactService.findAllContacts();

		// Then
		Assertions.assertEquals(expectedContacts.size(), actualContacts.size());
		Assertions.assertEquals(expectedContacts, actualContacts);
	}

	@Test
	public void testCreateContact() {
		// Given
		Contact contact = new Contact("Leo Messi", "messi@barca.com");
		when(contactRepository.save(Mockito.any(Contact.class))).thenReturn(contact);

		// When
		Contact savedContact = contactService.createContact(contact);

		// Then
		Assertions.assertEquals(contact, savedContact);
	}

	@Test
	public void testDeleteContact() {
		// Given
		Long contactId = 1L;
		when(contactRepository.findById(contactId)).thenReturn(java.util.Optional.of(new Contact("Leo Messi", "messi@barca.com")));
		Mockito.doNothing().when(contactRepository).deleteById(contactId);

		// When
		String result = contactService.deleteContact(contactId);

		// Then
		Assertions.assertEquals("Contact deleted successfully", result);
	}

	@Test
	public void testCreateContactWithNullName() {
		// Given
		Contact contact = new Contact(null, "messi@barca.com");

		// When/Then
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contactService.createContact(contact);
		});
	}

	@Test
	public void testCreateContactWithInvalidEmail() {
		// Given
		Contact contact = new Contact("Leo Messi", "email-email");

		// When/Then
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			contactService.createContact(contact);
		});
	}

	@Test
	void createContact_WithExistingEmail_ThrowsException() {
		// Arrange
		String email = "barca@barca.com";
		Contact existingContact = new Contact("Leo Messi", email);
		when(contactRepository.findByEmail(email)).thenReturn(existingContact);

		Contact newContact = new Contact("Pedri Gonzalez", email);

		// Act and Assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> contactService.createContact(newContact));
	}

	@Test
	void createContact_WithNullEmail_ThrowsException() {
		// Arrange
		Contact contact = new Contact("Leo Messi", null);

		// Act and Assert
		Assertions.assertThrows(IllegalArgumentException.class, () -> contactService.createContact(contact));
	}

}
