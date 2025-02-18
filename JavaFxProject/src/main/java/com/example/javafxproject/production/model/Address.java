package com.example.javafxproject.production.model;

import com.example.javafxproject.production.enums.City;

import java.util.Objects;

public class Address {
    String street;
    String houseNumber;
    City city;
    String postalCode;

    public Address(String street, String houseNumber, City city, String postalCode) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address address)) return false;
        return Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getHouseNumber(), address.getHouseNumber()) && getCity() == address.getCity() && Objects.equals(getPostalCode(), address.getPostalCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStreet(), getHouseNumber(), getCity(), getPostalCode());
    }
}
