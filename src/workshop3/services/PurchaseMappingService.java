package workshop3.services;

import workshop3.domain.Car;
import workshop3.domain.Location;
import workshop3.domain.Person;
import workshop3.domain.Purchase;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseMappingService {
    public Purchase mapPurchase(String line) {
        String[] row = line.split(",");
        long id = Long.parseLong(row[0]);
        String firstName = row[1];
        String lastName = row[2];
        String email = row[3];
        String ipAddress = row[4];
        String color = row[5];
        String carVin = row[6];
        String carCompany = row[7];
        String carModel = row[8];
        String carModelYear = row[9];
        BigDecimal carPrice = new BigDecimal(row[10].replace("\"", "").substring(1));
        String country = row[11];
        String city = row[12];
        LocalDate date = LocalDate.parse(row[13]);

        return new Purchase(
                id,
                new Person(firstName, lastName, email, ipAddress),
                new Car(color, carVin, carCompany, carModel, carModelYear, carPrice),
                new Location(country, city),
                date
        );
    }
}
