package com.example.address_book.repository;

import com.example.address_book.exception.AddressesBookNotFoundException;
import com.example.address_book.model.AddressBook;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AddressBookRepository {
    List<AddressBook> addresses = new ArrayList<>();

    public AddressBookRepository() {
        addresses.add(new AddressBook(UUID.randomUUID().toString(), "5th Avenue", "New York", "10001", "Jane", "Doe"));
        addresses.add(new AddressBook(UUID.randomUUID().toString(), "6th Avenue", "New York", "10002", "John", "Doe"));
    }

    public List<AddressBook> findAll() {
        return addresses;
    }

    public AddressBook findById(String id) throws AddressesBookNotFoundException {
        return addresses.stream().filter(address -> address.getId().equals(id)).findFirst().orElseThrow(AddressesBookNotFoundException::new);
    }

    public List<AddressBook> findByStreetName(String street) throws AddressesBookNotFoundException {
        var list = addresses.stream().filter(address -> address.getStreet().toLowerCase().contains(street.toLowerCase())).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new AddressesBookNotFoundException();
        }
        return list;
    }

    public List<AddressBook> findByLastName(String surname) throws AddressesBookNotFoundException {
        var list = addresses.stream().filter(address -> address.getSurname().toLowerCase().contains(surname.toLowerCase())).collect(Collectors.toList());
        if (list.isEmpty()) {
            throw new AddressesBookNotFoundException();
        }
        return list;
    }

    public AddressBook create(AddressBook address) {
        addresses.add(address);
        return address;
    }

    public void update(AddressBook address, String id) {
        AddressBook existing = addresses.stream().filter(a -> a.getId().equals(id)).findAny().orElseThrow(() -> new IllegalArgumentException("Stream not found"));
        int i = addresses.indexOf(existing);
        addresses.set(i, address);
    }

    public void delete(String id) {
        addresses.removeIf(addresses -> addresses.getId().equals(id));
    }
}
