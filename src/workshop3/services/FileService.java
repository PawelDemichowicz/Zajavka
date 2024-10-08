package workshop3.services;

import workshop3.domain.Purchase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    public static final String RESOURCES_CARS = "src/workshop3/resources/cars/";
    public static final String FILE_HEADER = "id,first_name,last_name,email,ip_address,color,car_vin,car_company," +
            "car_model,car_model_year,car_price,country,city,date";
    private final PurchaseMappingService purchaseMappingService = new PurchaseMappingService();

    public List<Purchase> loadData(Path path) {
        try (Stream<String> purchasesData = Files.lines(path)) {
            return purchasesData.skip(1)
                    .map(purchaseMappingService::mapPurchase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDataPerCarCompany(List<Purchase> purchases) {
        try {
            Files.createDirectories(Paths.get(RESOURCES_CARS));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map<Object, List<Purchase>> carCompanyList = purchases.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCar().getCarCompany(),
                        TreeMap::new,
                        Collectors.toList()
                ));

        for (Map.Entry<Object, List<Purchase>> entry : carCompanyList.entrySet()) {
            Path carCompanyPath = Paths.get(RESOURCES_CARS, "purchase-of-" + entry.getKey() + ".csv");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(carCompanyPath)) {
                bufferedWriter.write(FILE_HEADER);
                for (Purchase purchase : entry.getValue()) {
                    bufferedWriter.write(purchaseMappingService.mapPurchaseToCSV(purchase));
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
