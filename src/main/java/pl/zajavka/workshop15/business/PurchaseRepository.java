package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);

    void removeAll();

    void removeAll(String email);

    List<Purchase> findAll(String email);

    List<Purchase> findAll(String email, String productCode);
}
