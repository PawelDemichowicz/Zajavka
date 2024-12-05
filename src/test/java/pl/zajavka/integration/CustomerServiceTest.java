package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.domain.StoreFixtures;
import pl.zajavka.workshop15.business.*;
import pl.zajavka.workshop15.domain.*;
import pl.zajavka.workshop15.infrastructure.configuration.ApplicationConfiguration;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class CustomerServiceTest {

    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;
    private PurchaseService purchaseService;

    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(customerService);
        assertNotNull(opinionService);
        assertNotNull(producerService);
        assertNotNull(productService);
        assertNotNull(purchaseService);
        reloadDataService.loadRandomData();
    }

    @Test
    @DisplayName("Remove customer correctly")
    void thatCustomerIsRemoveCorrectly() {
        // given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        opinionService.create(StoreFixtures.someOpinion(customer, product1));

        assertEquals(customer, customerService.find(customer.getEmail()));

        // when
        customerService.remove(customer.getEmail());

        // then
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> customerService.find(customer.getEmail()));
        assertEquals(
                "Customer with email: [%s] is missing".formatted(customer.getEmail()),
                exception.getMessage()
        );
        assertTrue(purchaseService.findAll(customer.getEmail()).isEmpty());
        assertTrue(opinionService.findAll(customer.getEmail()).isEmpty());
    }

    @Test
    @DisplayName("Check if purchase and opinion is not removing when customer remove failed")
    void thatPurchaseAndOpinionIsNotRemovedWhenCustomerRemovingFails() {
        // given
        final Customer customer = customerService.create(StoreFixtures.someCustomer()
                .withDateOfBirth(LocalDate.of(1950, 10, 1)));
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        Purchase purchase1 = purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        Purchase purchase2 = purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        Opinion opinion = opinionService.create(StoreFixtures.someOpinion(customer, product1));

        assertEquals(customer, customerService.find(customer.getEmail()));

        // when, then
        RuntimeException exception
                = assertThrows(RuntimeException.class, () -> customerService.remove(customer.getEmail()));
        assertEquals(
                "Could not remove customer because he/she is older than 40",
                exception.getMessage()
        );

        assertEquals(
                List.of(
                        purchase1
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product1.getId()).build())
                                .withDateTime(purchase1.getDateTime().withOffsetSameInstant(ZoneOffset.UTC)),
                        purchase2
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product2.getId()).build())
                                .withDateTime(purchase2.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                ),
                purchaseService.findAll(customer.getEmail())
        );
        assertEquals(
                List.of(
                        opinion
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product1.getId()).build())
                                .withDateTime(opinion.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                ),
                opinionService.findAll(customer.getEmail())
        );
    }

    @Test
    @DisplayName("Check remove customers whose give unwanted opinions")
    void thatCustomerGivingUnwantedOpinionsAreRemoved() {
        // given
        reloadDataService.reloadData();
        assertEquals(100, customerService.findAll().size());

        // when
        customerService.removeUnwantedCustomers();

        // then
        assertEquals(67, customerService.findAll().size());
    }
}
