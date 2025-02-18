package com.example.javafxproject.production.model;

import com.example.javafxproject.production.abstractClass.NamedEntity;
import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Accommodation extends NamedEntity {
    Long id;
    Address address;
    BigDecimal regularPricePerNight;
    Discount discount;
    BigDecimal reducedPricePerNight;
    LocalDate checkInDate;
    LocalDate checkOutDate;
    Set<Passenger> passengers;

    public Accommodation(Long id, String name, Address address, BigDecimal regularPricePerNight, Discount discount, BigDecimal reducedPricePerNight,LocalDate checkInDate, LocalDate checkOutDate, Set<Passenger> passengers) {
        super(name);
        this.id = id;
        this.address = address;
        this.regularPricePerNight = regularPricePerNight;
        this.discount = discount;
        this.reducedPricePerNight=reducedPricePerNight;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.passengers = passengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BigDecimal getRegularPricePerNight() {
        return regularPricePerNight;
    }

    public void setRegularPricePerNight(BigDecimal regularPricePerNight) {
        this.regularPricePerNight = regularPricePerNight;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public BigDecimal getReducedPricePerNight() {
        return reducedPricePerNight;
    }

    public void setReducedPricePerNight(BigDecimal reducedPricePerNight) {
        this.reducedPricePerNight = reducedPricePerNight;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
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
        if (!(o instanceof Accommodation that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getRegularPricePerNight(), that.getRegularPricePerNight()) && Objects.equals(getDiscount(), that.getDiscount()) && Objects.equals(getReducedPricePerNight(), that.getReducedPricePerNight()) && Objects.equals(getCheckInDate(), that.getCheckInDate()) && Objects.equals(getCheckOutDate(), that.getCheckOutDate()) && Objects.equals(getPassengers(), that.getPassengers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getAddress(), getRegularPricePerNight(), getDiscount(), getReducedPricePerNight(), getCheckInDate(), getCheckOutDate(), getPassengers());
    }
}
