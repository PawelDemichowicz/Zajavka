package workshop3.services;

import workshop2.stream.Pair;
import workshop3.domain.Purchase;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileService {
    public static final String RESOURCES_CARS = "src/workshop3/resources/cars/";
    public static final String REPORTS = "src/workshop3/resources/reports/";
    public static final String RESOURCES_CARS_HEADER = "id,first_name,last_name,email,ip_address,color,car_vin,car_company," +
            "car_model,car_model_year,car_price,country,city,date";
    public static final String REPORTS_HEADER = "id,company,model,average_price,amount_of_purchases";

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
        Map<String, List<Purchase>> carCompanyList = getPurchasesByCompany(purchases);

        for (Map.Entry<String, List<Purchase>> entry : carCompanyList.entrySet()) {
            Path carCompanyPath = Paths.get(RESOURCES_CARS, "purchase-of-" + entry.getKey() + ".csv");
            List<String> rows = entry.getValue().stream()
                    .map(purchaseMappingService::mapPurchaseToCSV)
                    .collect(Collectors.toList());
            saveToFile(rows, carCompanyPath, RESOURCES_CARS_HEADER);
        }
    }

    public void generateReportWithSoldCarStats(Map<String, List<Purchase>> purchasesByCompany) {
        Map<String, Map<String, List<Purchase>>> purchasesGroupByCompanyAndModel = purchasesByCompany.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .collect(Collectors.groupingBy(
                                                innerEntry -> innerEntry.getCar().getCarModel()
                                        )
                                )));

        Map<String, Map<String, Pair<BigDecimal, Long>>> purchasesGroupByCompanyAndModelWithAveragePriceAndCount =
                purchasesGroupByCompanyAndModel.entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> entry.getValue().entrySet().stream()
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                innerEntry -> buildPair(innerEntry.getValue()),
                                                (first, second) -> first,
                                                TreeMap::new
                                        )),
                                (first, second) -> first,
                                TreeMap::new
                        ));

        AtomicInteger counter = new AtomicInteger(1);
        List<String> reportData = purchasesGroupByCompanyAndModelWithAveragePriceAndCount.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(innerEntry -> purchaseMappingService.mapPurchaseToReport(
                                counter.getAndIncrement(),
                                entry.getKey(),
                                innerEntry.getKey(),
                                innerEntry.getValue().getU(),
                                innerEntry.getValue().getV())))
                .collect(Collectors.toList());

        Path path = Paths.get(REPORTS, "purchases-report.csv");
        saveToFile(reportData, path, REPORTS_HEADER);
    }

    public static Map<String, List<Purchase>> getPurchasesByCompany(List<Purchase> purchases) {
        return purchases.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCar().getCarCompany(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

    private void saveToFile(List<String> data, Path path, String header) {
        try {
            Files.createDirectories(path.subpath(0, path.getNameCount() - 1));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write(header);
            bufferedWriter.newLine();
            for (String row : data) {
                bufferedWriter.write(row);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pair<BigDecimal, Long> buildPair(List<Purchase> purchaseList) {
        long numberOfPurchases = purchaseList.size();
        BigDecimal averagePrice = purchaseList.stream()
                .map(p -> p.getCar().getCarPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(numberOfPurchases), 2, RoundingMode.HALF_UP);
        return new Pair<>(averagePrice, numberOfPurchases);
    }
}
