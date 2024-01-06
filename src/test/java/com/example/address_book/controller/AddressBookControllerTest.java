package com.example.address_book.controller;

import com.example.address_book.model.AddressBook;
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
    private ResponseEntity<List<AddressBook>> findAllAddresses() {
        return restTemplate.exchange("/addresses",
                HttpMethod.GET,
                new HttpEntity<>(null),
                new ParameterizedTypeReference<List<AddressBook>>() {});
    }

}
