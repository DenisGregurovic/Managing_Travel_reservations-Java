package com.example.javafxproject.production.model;

import com.example.javafxproject.production.abstractClass.NamedEntity;
import com.example.javafxproject.production.enums.City;
import com.example.javafxproject.production.enums.TypeOfTransportation;
import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public class Transport extends NamedEntity {
    Long id;
    TypeOfTransportation vehicle;
    City startingPoint;
    City destination;
    LocalDate date;
    BigDecimal regularPrice;
    Discount discount;
    BigDecimal reducedPrice;

    Set<Passenger> passengers;

    public Transport(Long id, String name, TypeOfTransportation vehicle, City startingPoint, City destination, LocalDate date, BigDecimal regularPrice,Discount discount, BigDecimal reducedPrice,Set<Passenger> passengers) {
        super(name);
        this.id = id;
        this.vehicle = vehicle;
        this.startingPoint = startingPoint;
        this.destination = destination;
        this.date = date;
        this.regularPrice = regularPrice;
        this.discount=discount;
        this.reducedPrice=reducedPrice;
        this.passengers = passengers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfTransportation getVehicle() {
        return vehicle;
    }

    public void setVehicle(TypeOfTransportation vehicle) {
        this.vehicle = vehicle;
    }

    public City getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(City startingPoint) {
        this.startingPoint = startingPoint;
    }

    public City getDestination() {
        return destination;
    }

    public void setDestination(City destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
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

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transport transport)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), transport.getId()) && getVehicle() == transport.getVehicle() && getStartingPoint() == transport.getStartingPoint() && getDestination() == transport.getDestination() && Objects.equals(getDate(), transport.getDate()) && Objects.equals(getRegularPrice(), transport.getRegularPrice()) && Objects.equals(getDiscount(), transport.getDiscount()) && Objects.equals(getReducedPrice(), transport.getReducedPrice()) && Objects.equals(getPassengers(), transport.getPassengers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getVehicle(), getStartingPoint(), getDestination(), getDate(), getRegularPrice(), getDiscount(), getReducedPrice(), getPassengers());
    }
}
