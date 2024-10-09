package workshop3;

import workshop3.domain.Purchase;
import workshop3.services.FileService;
import workshop3.services.PrinterService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        PrinterService printerService = new PrinterService();
        Path path = Paths.get("src/workshop3/resources/client-car-purchase-spreadsheet.csv");

        List<Purchase> purchaseList = fileService.loadData(path);
        fileService.writeDataPerCarCompany(purchaseList);
        printerService.printCountedPurchasePerCompany();
        System.out.println();
        printerService.printCarCompanyPerFileSize();
        fileService.generateReportWithSoldCarStats(FileService.getPurchasesByCompany(purchaseList));

    }
}
