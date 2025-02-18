package production.builderPatterns;

import production.enums.City;
import production.model.Address;

public class AddressBuilder {
    private String street;
    private String houseNumber;
    private City city;
    private String postalCode;

    public AddressBuilder setStreet(String street) {
        this.street = street;
        return this;
    }

    public AddressBuilder setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public AddressBuilder setCity(City city) {
        this.city = city;
        return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public Address createAddress() {
        return new Address(street, houseNumber, city, postalCode);
    }
}