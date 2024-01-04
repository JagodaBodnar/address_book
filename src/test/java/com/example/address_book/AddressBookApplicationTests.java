package com.example.address_book;

import model.AddressesBook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AddressBookApplicationTests {

    @Test
    void create_new_address() {
        AddressesBook address = new AddressesBook();
        address.setId(UUID.randomUUID().toString());
        address.setCity("London");
        address.setName("John");
        address.setSurname("Doe");
        address.setStreet("Baker Street");
        address.setPostCode("12345");
        assertNotNull(address);
        assertEquals("London",address.getCity());
        assertEquals("John", address.getName());
        assertEquals("Doe",address.getSurname());
        assertEquals("Baker Street",address.getStreet());
        assertEquals("12345",address.getPostCode());

    }

}
