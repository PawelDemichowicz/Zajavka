package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Product;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Transactional
    public void removeAll() {
        productRepository.removeAll();
    }
}