package workshop3;

import workshop3.domain.Purchase;
import workshop3.services.FileService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        Path path = Paths.get("src/workshop3/resources/client-car-purchase-spreadsheet.csv");
        List<Purchase> purchaseList = fileService.loadData(path);
    }
}
