package com.example.javafxproject.production.model;

import com.example.javafxproject.production.abstractClass.NamedEntity;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger extends NamedEntity {
    Long id;
    String surname;
    String email;
    String phoneNumber;
    LocalDate dateOfBirth;

    public Passenger(Long id, String name, String surname, String email, String phoneNumber, LocalDate dateOfBirth) {
        super(name);
        this.id = id;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger passenger)) return false;
        return Objects.equals(getId(), passenger.getId()) && Objects.equals(getName(), passenger.getName()) && Objects.equals(getSurname(), passenger.getSurname()) && Objects.equals(getEmail(), passenger.getEmail()) && Objects.equals(getPhoneNumber(), passenger.getPhoneNumber()) && Objects.equals(getDateOfBirth(), passenger.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname(), getEmail(), getPhoneNumber(), getDateOfBirth());
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
