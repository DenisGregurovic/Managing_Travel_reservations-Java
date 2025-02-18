package com.example.visual.production.Entiteti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public record Popust(BigDecimal iznos) implements Serializable
{
    public BigDecimal iznos()
    {
        return iznos;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Popust popust)) return false;
        return Objects.equals(iznos, popust.iznos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iznos);
    }
}
