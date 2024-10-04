package workshop3.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Purchase {
    private final BigDecimal id;
    private final Person person;
    private final Car car;
    private final Location location;
    private final LocalDate date;

    public Purchase(BigDecimal id, Person person, Car car, Location location, LocalDate date) {
        this.id = id;
        this.person = person;
        this.car = car;
        this.location = location;
        this.date = date;
    }
}
