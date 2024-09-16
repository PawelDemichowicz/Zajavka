import java.util.Objects;

public class Person {
    private final String name;
    private final String surname;
    private final Integer counter;
    private boolean isVip;

    public Person(String name, String surname, Integer counter) {
        this.name = name;
        this.surname = surname;
        this.counter = counter;
    }

    public Person(String name, String surname, Integer counter, boolean isVip) {
        this.name = name;
        this.surname = surname;
        this.counter = counter;
        this.isVip = isVip;
    }

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Person)) return false;

        Person person = (Person) object;
        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(counter, person.counter);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(surname);
        result = 31 * result + Objects.hashCode(counter);
        return result;
    }

    @Override
    public String toString() {
        return name + '_' + surname + '_' + counter + (isVip ? "_VIP" : "");
    }
}
