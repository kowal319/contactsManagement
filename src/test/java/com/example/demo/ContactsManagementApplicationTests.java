package com.example.demo;

import com.example.demo.entity.Contact;
import com.example.demo.repository.ContactRepository;
import com.example.demo.service.ContactService;
import com.example.demo.service.Implementation.ContactServiceImplementation;
import jakarta.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ContactsManagementApplicationTests {

	@Test
	void contextLoads() {
	}

	private ContactService contactService;
	private ContactRepository contactRepository;
    private Validator validator;

	@BeforeEach
	public void setUp() {
		contactRepository = Mockito.mock(ContactRepository.class);
		contactService = new ContactServiceImplementation(contactRepository);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
		assertThrows(IllegalArgumentException.class, () -> {
			contactService.createContact(contact);
		});
	}

    @Test
    public void testValidEmail(){
        //Given
        String validEmail = "valid@email.com";
        Contact contact = new Contact();
        contact.setEmail(validEmail);

        //When
        String email = contact.getEmail();

        //Then
        Assertions.assertEquals(validEmail, email);
    }

    @Test
    public  void testInvalidEmail(){
        //Given
        String invalidEmail = "invalid.email";
        Contact contact  = new Contact();
        contact.setEmail(invalidEmail);

        //When/Then
       Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
       Assertions.assertFalse(violations.isEmpty());
       Assertions.assertEquals(1, violations.size());

       ConstraintViolation<Contact> violation = violations.iterator().next();
       Assertions.assertEquals("email", violation.getPropertyPath().toString());
       Assertions.assertEquals("musi być poprawnie sformatowanym adresem e-mail", violation.getMessage());
    }

	@Test
	void testWithExistingEmailThrowsException() {
		// Arrange
		String email = "barca@barca.com";
		Contact existingContact = new Contact("Leo Messi", email);
		when(contactRepository.findByEmail(email)).thenReturn(existingContact);

		Contact newContact = new Contact("Pedri Gonzalez", email);

		// Act and Assert
		assertThrows(IllegalArgumentException.class, () -> contactService.createContact(newContact));
	}

	@Test
	void testWithNullEmailThrowsException() {
		// Arrange
		Contact contact = new Contact("Leo Messi", null);

		// Act and Assert
		assertThrows(IllegalArgumentException.class, () -> contactService.createContact(contact));
	}

}
