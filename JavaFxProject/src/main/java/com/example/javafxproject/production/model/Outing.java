package com.example.javafxproject.production.model;

import com.example.javafxproject.production.abstractClass.NamedEntity;
import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Outing extends NamedEntity {
    Long id;
    String description;
    BigDecimal regularPrice;
    Discount discount;
    BigDecimal reducedPrice;
    LocalDate date;
    Address address;
    Set<Passenger> passengers;

    public Outing(Long id, String name, String description, BigDecimal regularPrice,Discount discount, BigDecimal reducedPrice,LocalDate date,Address address, Set<Passenger> passengers) {
        super(name);
        this.id = id;
        this.description = description;
        this.regularPrice = regularPrice;
        this.discount=discount;
        this.reducedPrice=reducedPrice;
        this.date = date;
        this.address=address;
        this.passengers = passengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal price) {
        this.reducedPrice = regularPrice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public BigDecimal getReducedPrice() {
        return reducedPrice;
    }

    public void setReducedPrice(BigDecimal reducedPrice) {
        this.reducedPrice = reducedPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Outing outing)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), outing.getId()) && Objects.equals(getDescription(), outing.getDescription()) && Objects.equals(getRegularPrice(), outing.getRegularPrice()) && Objects.equals(getDiscount(), outing.getDiscount()) && Objects.equals(getReducedPrice(), outing.getReducedPrice()) && Objects.equals(getDate(), outing.getDate()) && Objects.equals(getAddress(), outing.getAddress()) && Objects.equals(getPassengers(), outing.getPassengers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getDescription(), getRegularPrice(), getDiscount(), getReducedPrice(), getDate(), getAddress(), getPassengers());
    }
}
