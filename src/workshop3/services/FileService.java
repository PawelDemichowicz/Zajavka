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

public class FileService {
    private final PurchaseMappingService purchaseMappingService = new PurchaseMappingService();

    public List<Purchase> loadData(Path path) {
        try {
            return Files.lines(path)
                    .skip(1)
                    .map(purchaseMappingService::mapPurchase)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeDataPerCarCompany(List<Purchase> purchases) {
        Path basepath = Paths.get("src/workshop3/resources/cars");
        Map<Object, List<Purchase>> carCompanyList = purchases.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getCar().getCarCompany(),
                        TreeMap::new,
                        Collectors.toList()
                ));

        for (Map.Entry<Object, List<Purchase>> entry : carCompanyList.entrySet()) {
            Path carCompanyPath = Paths.get(basepath.toString(), entry.getKey() + ".txt");
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(carCompanyPath)) {
                for (Purchase purchase : entry.getValue()) {
                    bufferedWriter.write(String.valueOf(purchase));
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
