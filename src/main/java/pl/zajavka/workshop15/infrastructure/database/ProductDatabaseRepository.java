package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.ProductRepository;
import pl.zajavka.workshop15.domain.Product;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.PRODUCT_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class ProductDatabaseRepository implements ProductRepository {

    private static final String SELECT_ALL = "SELECT * FROM PRODUCT";
    private static final String SELECT_WHERE_PRODUCT_CODE = "SELECT * FROM PRODUCT WHERE PRODUCT_CODE = :productCode";
    private static final String DELETE_ALL = "DELETE FROM PRODUCT WHERE 1=1";
    private static final String DELETE_BY_PRODUCT_CODE = "DELETE FROM PRODUCT WHERE PRODUCT_CODE = :productCode";

    private final SimpleDriverDataSource simpleDriverDataSource;

    private final DatabaseMapper databaseMapper;

    @Override
    public Product create(Product product) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(PRODUCT_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.PRODUCT_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(product);
        Number productId = jdbcInsert.executeAndReturnKey(params);
        return product.withId((long) productId.intValue());
    }

    @Override
    public Optional<Product> find(String productCode) {
        final var jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);

        Map<String, Object> params = Map.of("productCode", productCode);
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_WHERE_PRODUCT_CODE, params, databaseMapper::mapProduct)
            );
        } catch (Exception e) {
            log.warn("Trying to find non-existing customer: [{}]", productCode);
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL, databaseMapper::mapProduct);
    }

    @Override
    public void remove(String productCode) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_BY_PRODUCT_CODE, Map.of("productCode", productCode));
    }

    @Override
    public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).execute(DELETE_ALL);
    }
}
