package com.example.address_book.controller;

import com.example.address_book.exception.AddressesBookNotFoundException;
import com.example.address_book.model.AddressBook;
import com.example.address_book.repository.AddressBookRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/addresses")
public class AddressBookController {

    private final AddressBookRepository repository;

    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public List<AddressBook> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public AddressBook findById(@PathVariable String id) throws AddressesBookNotFoundException {
        return repository.findById(id);
    }

    @GetMapping("/street/{street}")
    public AddressBook findByStreetName(@PathVariable String street) throws AddressesBookNotFoundException {
        return repository.findByStreetName(street);
    }

    @GetMapping("/name/{name}")
    public AddressBook findByName(@PathVariable String name) throws AddressesBookNotFoundException {
        return repository.findByName(name);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AddressBook create(@Valid @RequestBody AddressBook address) {
        return repository.create(address);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody AddressBook address, @PathVariable String id) {
        repository.update(address, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        repository.delete(id);
    }
}
