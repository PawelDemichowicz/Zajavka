package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Customer;
import pl.zajavka.workshop15.domain.Product;
import pl.zajavka.workshop15.domain.Purchase;

import java.time.OffsetDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCarService {

    private CustomerService customerService;
    private ProductService productService;
    private PurchaseService purchaseService;

    @Transactional
    public void makeAPurchase(String email, String productCode, int quantity) {
        Customer customer = customerService.find(email);
        Product product = productService.find(productCode);
        Purchase purchase = purchaseService.create(Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .dateTime(OffsetDateTime.now())
                .build());

        log.info("Customer: [{}] made a purchase for product: [{}], quantity: [{}]",
                email, productCode, quantity);
        log.debug("Customer: [{}] made a purchase for product: [{}], quantity: [{}], purchase: [{}]",
                email, productCode, quantity, purchase);
    }
}
