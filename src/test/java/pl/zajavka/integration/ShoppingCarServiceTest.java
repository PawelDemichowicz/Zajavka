package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.domain.StoreFixtures;
import pl.zajavka.workshop15.business.*;
import pl.zajavka.workshop15.domain.Customer;
import pl.zajavka.workshop15.domain.Producer;
import pl.zajavka.workshop15.domain.Product;
import pl.zajavka.workshop15.infrastructure.configuration.ApplicationConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class ShoppingCarServiceTest {

    private ShoppingCarService shoppingCarService;
    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;
    private PurchaseService purchaseService;

    @BeforeEach
    public void setUp() {
        assertNotNull(shoppingCarService);
        assertNotNull(reloadDataService);
        assertNotNull(customerService);
        assertNotNull(opinionService);
        assertNotNull(producerService);
        assertNotNull(productService);
        assertNotNull(purchaseService);
        reloadDataService.reloadData();
    }

    @Test
    void thatProductCanBeBoughtByCustomer() {
        // given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product = productService.create(StoreFixtures.someProduct1(producer));
        final var before = purchaseService.findAll(customer.getEmail(), product.getProductCode());

        // when
        shoppingCarService.makeAPurchase(customer.getEmail(), product.getProductCode(), 10);

        // then
        final var after = purchaseService.findAll(customer.getEmail(), product.getProductCode());
        assertEquals(before.size() + 1, after.size());
    }
}
