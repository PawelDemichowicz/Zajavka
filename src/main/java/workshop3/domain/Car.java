package workshop3.domain;

import java.math.BigDecimal;

public class Car {
    private final String color;
    private final String carVin;
    private final String carCompany;
    private final String carModel;
    private final String carModelYear;
    private final BigDecimal carPrice;

    public Car(String color, String carVin, String carCompany, String carModel, String carModelYear, BigDecimal carPrice) {
        this.color = color;
        this.carVin = carVin;
        this.carCompany = carCompany;
        this.carModel = carModel;
        this.carModelYear = carModelYear;
        this.carPrice = carPrice;
    }

    public String getColor() {
        return color;
    }

    public String getCarVin() {
        return carVin;
    }

    public String getCarCompany() {
        return carCompany;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarModelYear() {
        return carModelYear;
    }

    public BigDecimal getCarPrice() {
        return carPrice;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", carVin='" + carVin + '\'' +
                ", carCompany='" + carCompany + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carModelYear='" + carModelYear + '\'' +
                ", carPrice=" + carPrice +
                '}';
    }
}
