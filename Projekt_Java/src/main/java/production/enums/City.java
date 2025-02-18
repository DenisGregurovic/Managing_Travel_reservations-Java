package production.enums;

public enum City {
    ZAGREB("Zagreb","10000"),
    LONDON("London","32122"),
    PARIS("Paris","75001"),
    DUBAI("Dubai","25314"),
    ISTANBUL("Istanbul","34000"),
    ROME("Rome","00010"),
    TOKYO("Tokyo","1000000"),
    BERLIN("Berlin","10115"),
    AMSTERDAM("Amsterdam","1011"),
    MADRID("Madrid","28001"),
    LISABON("Lisabon","1000001"),
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
