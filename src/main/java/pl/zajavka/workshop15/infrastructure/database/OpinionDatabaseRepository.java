package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.OpinionRepository;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.util.Map;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.OPINION_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {

    private final SimpleDriverDataSource simpleDriverDataSource;

    private final DatabaseMapper databaseMapper;

    @Override
    public Opinion create(Opinion opinion) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(simpleDriverDataSource)
                .withTableName(OPINION_TABLE)
                .usingGeneratedKeyColumns(DatabaseConfiguration.OPINION_TABLE_PKEY.toLowerCase());

        Map<String, ?> params = databaseMapper.map(opinion);
        Number opinionId = jdbcInsert.executeAndReturnKey(params);
        return opinion.withId((long) opinionId.intValue());
    }
}
