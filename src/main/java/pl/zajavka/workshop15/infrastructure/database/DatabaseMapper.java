package pl.zajavka.workshop15.infrastructure.database;

import org.springframework.stereotype.Component;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.domain.Product;
import pl.zajavka.workshop15.domain.Purchase;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class DatabaseMapper {

    private static final DateTimeFormatter DATABASE_DATE_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

    public Map<String, ?> map(Product product) {
        return Map.of(
                "product_code", product.getProductCode(),
                "product_name", product.getProductName(),
                "product_price", product.getProductPrice(),
                "adults_only", product.getAdultsOnly(),
                "description", product.getDescription(),
                "producer_id", product.getProducer().getId()
        );
    }

    public Map<String, ?> map(Opinion opinion) {
        return Map.of(
                "customer_id", opinion.getCustomer().getId(),
                "product_id", opinion.getProduct().getId(),
                "stars", opinion.getStars(),
                "comment", opinion.getComment(),
                "date_time", DATABASE_DATE_FORMAT.format(opinion.getDateTime())
        );
    }

    public Map<String, ?> map(Purchase purchase) {
        return Map.of(
                "customer_id", purchase.getCustomer().getId(),
                "product_id", purchase.getProduct().getId(),
                "quantity", purchase.getQuantity(),
                "date_time", DATABASE_DATE_FORMAT.format(purchase.getDateTime())
        );
    }
}
