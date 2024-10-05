package workshop3.services;

import workshop3.domain.Purchase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
}
