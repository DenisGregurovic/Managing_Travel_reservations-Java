package com.example.javafxproject.production.enums;

public enum City {
    ZAGREB("Zagreb","10000"),
    LONDON("London","SW1A 2"),
    PARIS("Paris","75001"),
    DUBAI("Dubai","25314"),
    ISTANBUL("Istanul","34000"),
    ROME("Rome","00010"),
    TOKYO("Tokyo","100-0000"),
    BERLIN("Berlin","10115"),
    AMSTERDAM("Amsterdam","1011 AA"),
    MADRID("Madrid","28001"),
    LISABON("Lisabon","1000-001"),
    NEW_YORK("New York","10007"),
    HONG_KONG("Hong Kong","999077"),
    LAS_VEGAS("Las Vegas","89101"),
    BEOGRAD("Beograd","11000");

    private final String name;
    private final String postalCode;

    City(String name, String postalCode) {
        this.name = name;
        this.postalCode = postalCode;
    }

    public String getName() {
        return name;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
