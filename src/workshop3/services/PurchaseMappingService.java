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

    public String mapPurchaseToCSV(Purchase purchase) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                purchase.getId(),
                purchase.getPerson().getFirstName(),
                purchase.getPerson().getLastName(),
                purchase.getPerson().getEmail(),
                purchase.getPerson().getIpAddress(),
                purchase.getCar().getColor(),
                purchase.getCar().getCarVin(),
                purchase.getCar().getCarCompany(),
                purchase.getCar().getCarModel(),
                purchase.getCar().getCarModelYear(),
                purchase.getCar().getCarPrice(),
                purchase.getLocation().getCountry(),
                purchase.getLocation().getCity(),
                purchase.getDate());
    }

    public String mapPurchaseToReport(int id, String company, String model, BigDecimal averagePrice, long count) {
        return String.format("%s,%s,%s,%s,%s", id, company, model, averagePrice, count);
    }

    public String mapAmountPurchasesWithDateToReport(int id, LocalDate date, long count) {
        return String.format("%s,%s,%s", id, date, count);
    }
}
