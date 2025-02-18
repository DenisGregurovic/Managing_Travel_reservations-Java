package com.example.javafxproject.production.model;

import java.math.BigDecimal;

public sealed interface NatureAccommodation permits Camp {
    BigDecimal area();
}
