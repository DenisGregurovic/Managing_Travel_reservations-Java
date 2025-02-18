package com.example.javafxproject.production.model;

import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Set;

public class Hostel extends Accommodation implements RoomAccommodation {

    Integer numberOfPassengersPerRoom;

    public Hostel(Long id, String name, Address address, BigDecimal regularPricePerNight, Discount discount, BigDecimal reducedPricePerNight, LocalDate checkInDate, LocalDate checkOutDate, Set<Passenger> passengers, Integer numberOfPassengersPerRoom) {
        super(id, name, address, regularPricePerNight, discount, reducedPricePerNight, checkInDate, checkOutDate, passengers);
        this.numberOfPassengersPerRoom=numberOfPassengersPerRoom;
    }
    public Hostel(Accommodation accommodation,Integer numberOfPassengersPerRoom) {
        super(accommodation.getId(),accommodation.getName(),accommodation.getAddress(),accommodation.getRegularPricePerNight(),accommodation.getDiscount(),accommodation.getReducedPricePerNight(),accommodation.getCheckInDate(),accommodation.getCheckOutDate(),accommodation.getPassengers());
        this.numberOfPassengersPerRoom=numberOfPassengersPerRoom;
    }

    public Integer getNumberOfPassengersPerRoom() {
        return numberOfPassengersPerRoom;
    }

    public void setNumberOfPassengersPerRoom(Integer numberOfPassengersPerRoom) {
        this.numberOfPassengersPerRoom = numberOfPassengersPerRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hostel hostel)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(numberOfPassengersPerRoom, hostel.numberOfPassengersPerRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), numberOfPassengersPerRoom);
    }

    @Override
    public BigDecimal calculatePricePerNight(Set<Passenger> passengers) {
        return reducedPricePerNight.multiply(BigDecimal.valueOf(passengers.size()));
    }

    @Override
    public BigDecimal calculateFinalPrice() {
        Period differenceBetweenDatesPeriod = Period.between(checkInDate,checkOutDate);
        int differenceBetweenDatesInt=differenceBetweenDatesPeriod.getDays();
        BigDecimal differenceBetweenDatesBigDecimal=BigDecimal.valueOf(differenceBetweenDatesInt);
        return reducedPricePerNight.multiply(BigDecimal.valueOf(passengers.size())).multiply(differenceBetweenDatesBigDecimal);
    }
}
