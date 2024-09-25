package workshop2.stream;

import java.math.BigDecimal;

public class Money {
    private final BigDecimal value;
    private final Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    public enum Currency {
        PLN,
        EUR
    }
}
