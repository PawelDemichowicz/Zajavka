package workshop3.domain;

import java.time.LocalDate;

public class Purchase {
    private final long id;
    private final Person person;
    private final Car car;
    private final Location location;
    private final LocalDate date;

    public Purchase(long id, Person person, Car car, Location location, LocalDate date) {
        this.id = id;
        this.person = person;
        this.car = car;
        this.location = location;
        this.date = date;
    }

    public Car getCar() {
        return car;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", person=" + person +
                ", car=" + car +
                ", location=" + location +
                ", date=" + date +
                '}';
    }
}
