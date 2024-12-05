package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Product;

import java.util.List;

public interface ProductRepository {
    Product create(Product product);

    List<Product> findAll();

    void removeAll();
}
