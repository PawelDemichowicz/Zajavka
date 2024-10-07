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
        Path carsPath = Paths.get("src/workshop3/resources/cars");

        try (Stream<Path> carPaths = Files.list(carsPath)) {
            TreeMap<String, Long> carCompanyPurchases = carPaths.collect(Collectors.toMap(
                    p -> p.getFileName().toString().replace(".txt", ""),
                    PrinterService::countPurchasesByCompany,
                    Long::sum,
                    TreeMap::new
            ));

            carCompanyPurchases.entrySet().stream()
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .forEach(entry -> System.out.printf("%s: %s%n", entry.getKey(), entry.getValue()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static long countPurchasesByCompany(Path p) {
        try (Stream<String> rows = Files.lines(p)) {
            return rows.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
