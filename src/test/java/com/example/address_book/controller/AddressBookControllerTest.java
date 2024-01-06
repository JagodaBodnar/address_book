package com.example.address_book.controller;

import com.example.address_book.model.AddressBook;
import org.apache.coyote.Response;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressBookControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    @Order(1)
    void findAll() {
        ResponseEntity<List<AddressBook>> entity = findAllAddresses();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, entity.getHeaders().getContentType());
        assertEquals(2,entity.getBody().size());

    }

    @Test
    @Order(2)
    void findById() {
        AddressBook existing = findAllAddresses().getBody().get(1);
        String id = existing.getId();
        String street = "6th Avenue";
        String city = "New York";
        String postCode = "10002";
        AddressBook address =  restTemplate.getForObject("/addresses/" + id, AddressBook.class);
        assertEquals(id, address.getId());
        assertEquals(street, address.getStreet());
        assertEquals(city, address.getCity());
        assertEquals(postCode, address.getPostCode());
    }
    @Test
    @Order(3)
    void create() {
        String id = UUID.randomUUID().toString();
        AddressBook address = new AddressBook(
                id,
                "Test Avenue",
                "Test City",
                "123456",
                "Test Name",
                "Test Surname"
        );
        ResponseEntity<AddressBook> entity =  restTemplate.postForEntity("/addresses", address, AddressBook.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
        assertEquals(3, findAllAddresses().getBody().size());
        AddressBook newAddress = entity.getBody();
        assertEquals(id, newAddress.getId());
        assertEquals("Test Avenue", newAddress.getStreet());
        assertEquals("Test City", newAddress.getCity());
        assertEquals("123456", newAddress.getPostCode());
        assertEquals("Test Name", newAddress.getName());
        assertEquals("Test Surname", newAddress.getSurname());

    }

    @Test
    @Order(4)
    void update(){
        AddressBook existing = findAllAddresses().getBody().get(1);
        AddressBook address = new AddressBook(
                existing.getId(),
                "New Street",
                existing.getCity(),
                existing.getPostCode(),
                existing.getName(),
                existing.getSurname()
        );
        ResponseEntity<AddressBook> entity = restTemplate.exchange("/addresses/" + existing.getId(), HttpMethod.PUT, new HttpEntity<>(address), AddressBook.class);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @Order(5)
    void delete(){
        AddressBook existing =  findAllAddresses().getBody().get(0);
        ResponseEntity<AddressBook> entity =  restTemplate.exchange("/addresses/" + existing.getId(), HttpMethod.DELETE, null, AddressBook.class);
        assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
        assertEquals(1, findAllAddresses().getBody().size());
    }

    private ResponseEntity<List<AddressBook>> findAllAddresses() {
        return restTemplate.exchange("/addresses",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<List<AddressBook>>() {});
    }

}
