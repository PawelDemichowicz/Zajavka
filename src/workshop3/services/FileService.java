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
import java.time.LocalDate;
import java.util.Comparator;
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
    public static final String PURCHASES_BY_COMPANY_AND_CAR = "id,company,model,average_price,amount_of_purchases";
    public static final String PURCHASES_BY_DATE = "id,date,amount_of_purchases";

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

        Path path = Paths.get(REPORTS, "purchases-per-company-and-model-report.csv");
        saveToFile(reportData, path, PURCHASES_BY_COMPANY_AND_CAR);
    }

    public void generateReportWithSoldCarsPerDate(List<Purchase> purchases) {
        TreeMap<LocalDate, Long> mapByDate = purchases.stream()
                .collect(Collectors.groupingBy(
                        Purchase::getDate,
                        TreeMap::new,
                        Collectors.counting()
                ));

        AtomicInteger byDateCounter = new AtomicInteger(1);
        List<String> daysWithSoldCarsSortedByDate = mapByDate.entrySet().stream()
                .map(entry -> purchaseMappingService.mapAmountPurchasesWithDateToReport(
                        byDateCounter.getAndIncrement(),
                        entry.getKey(),
                        entry.getValue()))
                .collect(Collectors.toList());

        Path byDateCounterPath = Paths.get(REPORTS, "purchases-sorted-by-date-report.csv");
        saveToFile(daysWithSoldCarsSortedByDate, byDateCounterPath, PURCHASES_BY_DATE);

        AtomicInteger byCountCounter = new AtomicInteger(1);
        List<String> daysWithSoldCarsSortedByCount = mapByDate.entrySet().stream()
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing((Pair<LocalDate, Long> p) -> p.getV()).reversed())
                .map(pair -> purchaseMappingService.mapAmountPurchasesWithDateToReport(
                        byCountCounter.getAndIncrement(),
                        pair.getU(),
                        pair.getV()))
                .collect(Collectors.toList());

        Path byCountPath = Paths.get(REPORTS, "purchases-sorted-by-count-report.csv");
        saveToFile(daysWithSoldCarsSortedByCount, byCountPath, PURCHASES_BY_DATE);
    }

    public Map<String, List<Purchase>> getPurchasesByCompany(List<Purchase> purchases) {
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
