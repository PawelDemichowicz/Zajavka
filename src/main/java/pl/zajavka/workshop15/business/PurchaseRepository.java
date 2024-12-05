package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);

    List<Purchase> findAll();

    List<Purchase> findAll(String email);

    List<Purchase> findAll(String email, String productCode);

    List<Purchase> findAllByProductCode(String productCode);

    void removeAll();

    void removeAll(String email);

    void removeAllByProductCode(String productCode);
}
