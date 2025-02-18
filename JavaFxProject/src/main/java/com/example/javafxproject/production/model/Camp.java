package com.example.javafxproject.production.model;

import com.example.javafxproject.production.records.Discount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

public non-sealed class Camp extends Accommodation implements NatureAccommodation {

    BigDecimal area;

    public Camp(Long id, String name, Address address, BigDecimal regularPricePerNight, Discount discount, BigDecimal reducedPricePerNight, LocalDate checkInDate, LocalDate checkOutDate, Set<Passenger> passengers, BigDecimal area) {
        super(id, name, address, regularPricePerNight, discount, reducedPricePerNight, checkInDate, checkOutDate, passengers);
        this.area=area;
    }
    public  Camp(Accommodation accommodation,BigDecimal area)
    {
        super(accommodation.getId(),accommodation.getName(),accommodation.getAddress(),accommodation.getRegularPricePerNight(),accommodation.getDiscount(),accommodation.getReducedPricePerNight(), accommodation.getCheckInDate(),accommodation.getCheckOutDate(),accommodation.getPassengers());
        this.area=area;
    }
    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Camp camp)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getArea(), camp.getArea());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getArea());
    }

    @Override
    public BigDecimal area() {
        return area;
    }
}
