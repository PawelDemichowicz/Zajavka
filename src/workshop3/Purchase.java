package workshop3;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Purchase {
    private final BigDecimal id;
    private final Person person;
    private final Car car;
    private final Location location;
    private final LocalDateTime date;

    public Purchase(BigDecimal id, Person person, Car car, Location location, LocalDateTime date) {
        this.id = id;
        this.person = person;
        this.car = car;
        this.location = location;
        this.date = date;
    }
}
