package workshop3.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrinterService {
    public void printCountedPurchasePerCompany() {
        Path carsPath = Paths.get(FileService.RESOURCES_CARS);

        try (Stream<Path> carPaths = Files.list(carsPath)) {
            TreeMap<String, Long> carCompanyPurchases = carPaths.collect(Collectors.toMap(
                    path -> path.getFileName().toString().replace(".csv", ""),
                    PrinterService::countPurchasesByCompany,
                    Long::sum,
                    TreeMap::new
            ));

            carCompanyPurchases.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> System.out.printf("%s: %s%n",
                            entry.getKey().replace("purchase-of-", ""),
                            entry.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long countPurchasesByCompany(Path path) {
        try (Stream<String> rows = Files.lines(path)) {
            return rows.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printCarCompanyPerFileSize() {
        Path carsPath = Paths.get(FileService.RESOURCES_CARS);

        try (Stream<Path> carPaths = Files.list(carsPath)) {
            TreeMap<String, Long> carCompanyPurchases = carPaths.collect(Collectors.toMap(
                    path -> path.getFileName().toString().replace(".csv", ""),
                    PrinterService::countPathSize,
                    Long::sum,
                    TreeMap::new
            ));

            carCompanyPurchases.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByKey().reversed())
                    .forEach(entry -> System.out.printf("%s: %s%n",
                            entry.getKey().replace("purchase-of-", ""),
                            entry.getValue()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long countPathSize(Path path) {
        try {
            return Files.size(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
