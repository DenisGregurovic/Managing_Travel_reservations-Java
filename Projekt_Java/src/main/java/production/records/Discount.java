package production.records;

import java.math.BigDecimal;
import java.util.Objects;

public record Discount(BigDecimal discountAmount) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Discount discount)) return false;
        return Objects.equals(discountAmount, discount.discountAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountAmount);
    }
}
