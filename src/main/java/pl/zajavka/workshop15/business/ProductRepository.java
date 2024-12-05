package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Product;

public interface ProductRepository {
    Product create(Product product);

    void removeAll();
}
