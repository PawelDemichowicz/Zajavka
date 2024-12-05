package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.workshop15.business.OpinionService;
import pl.zajavka.workshop15.business.ProductService;
import pl.zajavka.workshop15.business.PurchaseService;
import pl.zajavka.workshop15.business.ReloadDataService;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.domain.Product;
import pl.zajavka.workshop15.domain.Purchase;
import pl.zajavka.workshop15.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor_ = @__(@Autowired))
public class ProductServiceTest {

    private ReloadDataService reloadDataService;
    private OpinionService opinionService;
    private ProductService productService;
    private PurchaseService purchaseService;

    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(opinionService);
        assertNotNull(productService);
        assertNotNull(purchaseService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Check if product is remove with all relations")
    void thatProductIsWiped() {
        // given
        final var productCode = "68084-618";
        productService.find(productCode);
        List<Opinion> opinionsBefore = opinionService.findAllByProductCode(productCode);
        List<Purchase> purchasesBefore = purchaseService.findAllByProductCode(productCode);

        assertEquals(3, opinionsBefore.size());
        assertEquals(4, purchasesBefore.size());

        // when
        productService.removeCompletely(productCode);

        // then
        Throwable exception = assertThrows(RuntimeException.class, () -> productService.find(productCode));
        assertEquals("Product with product code: [%s] is missing".formatted(productCode), exception.getMessage());

        assertTrue(opinionService.findAllByProductCode(productCode).isEmpty());
        assertTrue(purchaseService.findAllByProductCode(productCode).isEmpty());

    }
}
