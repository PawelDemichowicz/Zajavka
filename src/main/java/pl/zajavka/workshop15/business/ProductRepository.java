package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);

    List<Product> findAll();

    void removeAll();

    Optional<Product> find(String productCode);

    void remove(String productCode);
}
