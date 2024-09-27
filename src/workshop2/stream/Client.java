package workshop2.stream;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

public class Client implements Comparable<Client> {

    private final String id;
    private final String name;
    private final String surname;
    private final BigInteger pesel;
    private final String city;

    public Client(String id, String name, String surname, BigInteger pesel, String city) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public BigInteger getPesel() {
        return pesel;
    }

    public Integer getAge() {
        String pesel = String.valueOf(this.getPesel());
        int yearOfBirth = Integer.parseInt(pesel.substring(0, 2));
        int monthOfBirth = Integer.parseInt(pesel.substring(2, 4));

        if (monthOfBirth > 20) {
            yearOfBirth += 2000;
        } else {
            yearOfBirth += 1900;
        }

        return LocalDate.now().getYear() - yearOfBirth;
    }

    @Override
    public int compareTo(final Client o) {
        return this.id.compareTo(o.id);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Client client = (Client) o;
        return Objects.equals(pesel, client.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", pesel=" + pesel +
                ", city='" + city + '\'' +
                '}';
    }
}