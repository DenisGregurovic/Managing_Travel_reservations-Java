package com.example.javafxproject.production.model;

import java.math.BigDecimal;
import java.util.Set;

public interface RoomAccommodation {
    BigDecimal calculatePricePerNight(Set<Passenger> passengers);
    BigDecimal calculateFinalPrice();
}
