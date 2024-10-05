package workshop3.domain;

public class Location {
    private final String country;
    private final String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }

    @Override
    public String toString() {
        return "Location{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
