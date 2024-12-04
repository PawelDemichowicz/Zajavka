package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.PurchaseRepository;
import pl.zajavka.workshop15.domain.Purchase;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.util.Map;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.PURCHASE_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class PurchaseDatabaseRepository implements PurchaseRepository {

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
}
