public class Person {
    private final String name;
    private final String surname;
    private final Integer counter;

    public Person(String name, String surname, Integer counter) {
        this.name = name;
        this.surname = surname;
        this.counter = counter;
    }

    @Override
    public String toString() {
        return name + '_' + surname + '_' + counter;
    }
}
