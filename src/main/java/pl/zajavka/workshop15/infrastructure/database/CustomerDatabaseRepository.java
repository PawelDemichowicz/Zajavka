package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.CustomerRepository;
import pl.zajavka.workshop15.domain.Customer;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.time.LocalDate;
import java.util.List;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.CUSTOMER_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDatabaseRepository implements CustomerRepository {

    private final SimpleDriverDataSource simpleDriverDataSource;

    @Override
    public Customer create(Customer customer) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(CUSTOMER_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.CUSTOMER_TABLE_PKEY.toLowerCase());

        Number customerId = jdbcInsert.executeAndReturnKey(new BeanPropertySqlParameterSource(customer));
        return customer.withId((long) customerId.intValue());
    }
}
