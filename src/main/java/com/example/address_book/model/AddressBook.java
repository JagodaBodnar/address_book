package com.example.address_book.model;


import jakarta.validation.constraints.NotEmpty;

public class AddressBook {
    private String id;
    private String street;
    private String city;
    private String postCode;
    private String name;
    private String surname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
    public AddressBook(){}

    public AddressBook(@NotEmpty String id,@NotEmpty String street,@NotEmpty String city,@NotEmpty String postCode,@NotEmpty String name,@NotEmpty String surname) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.name = name;
        this.surname = surname;
    }
}
