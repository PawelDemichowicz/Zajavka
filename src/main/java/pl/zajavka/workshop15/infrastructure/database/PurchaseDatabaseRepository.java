package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.PurchaseRepository;
import pl.zajavka.workshop15.domain.Purchase;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.PURCHASE_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {

    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL = """
            SELECT * FROM PURCHASE AS PUR
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID
                WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
            """;
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE = """
            SELECT * FROM PURCHASE AS PUR
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = PUR.CUSTOMER_ID
                INNER JOIN PRODUCT AS PROD ON PROD.ID = PUR.PRODUCT_ID
                WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
            """;
    private static final String SELECT_ALL = "SELECT * FROM PURCHASE";

    private static final String SELECT_ALL_WHERE_PRODUCT_CODE = """
            SELECT * FROM PURCHASE AS PUR
                INNER JOIN PRODUCT AS PROD ON PROD.ID = PUR.PRODUCT_ID
                WHERE PROD.PRODUCT_CODE = :productCode
                ORDER BY DATE_TIME
            """;

    private static final String DELETE_ALL = "DELETE FROM PURCHASE WHERE 1=1";

    private static final String DELETE_ALL_WHERE_CUSTOMER_EMAIL =
            "DELETE FROM PURCHASE WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
    private static final String DELETE_ALL_WHERE_PRODUCT_CODE =
            "DELETE FROM PURCHASE WHERE PRODUCT_ID IN (SELECT ID FROM PRODUCT WHERE PRODUCT_CODE = :productCode)";

    private final SimpleDriverDataSource simpleDriverDataSource;
    private final DatabaseMapper databaseMapper;

    @Override
    public Purchase create(Purchase purchase) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(PURCHASE_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PURCHASE_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(purchase);
        Number purchaseId = jdbcInsert.executeAndReturnKey(params);
        return purchase.withId((long) purchaseId.intValue());
    }

    @Override
    public List<Purchase> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL, databaseMapper::mapPurchase);
    }


    @Override
    public List<Purchase> findAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate
                .query(SELECT_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email), databaseMapper::mapPurchase);
    }

    @Override
    public List<Purchase> findAll(String email, String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(
                SELECT_ALL_WHERE_CUSTOMER_EMAIL_AND_PRODUCT_CODE,
                Map.of(
                        "email", email,
                        "product_code", productCode
                ),
                databaseMapper::mapPurchase
        );
    }

    @Override
    public List<Purchase> findAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_WHERE_PRODUCT_CODE, Map.of("productCode", productCode), databaseMapper::mapPurchase);
    }

    @Override
    public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).execute(DELETE_ALL);
    }

    @Override
    public void removeAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
    }

    @Override
    public void removeAllByProductCode(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_PRODUCT_CODE, Map.of("productCode", productCode));
    }
}
