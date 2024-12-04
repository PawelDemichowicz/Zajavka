package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Purchase;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);
}
