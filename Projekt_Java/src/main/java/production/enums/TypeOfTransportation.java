package production.enums;

import java.math.BigDecimal;

public enum TypeOfTransportation {
    PLANE("Plane",new BigDecimal(800)),
    TRAIN("Train",new BigDecimal(120)),
    SHIP("Ship",new BigDecimal(40)),
    BUS("Bus",new BigDecimal(80));
    private final String name;
    private final BigDecimal speed;

    TypeOfTransportation(String name, BigDecimal speed) {
        this.name = name;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getSpeed() {
        return speed;
    }
}
