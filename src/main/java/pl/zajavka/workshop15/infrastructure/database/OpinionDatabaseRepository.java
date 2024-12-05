package pl.zajavka.workshop15.infrastructure.database;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.stereotype.Repository;
import pl.zajavka.workshop15.business.OpinionRepository;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration;

import java.util.List;
import java.util.Map;

import static pl.zajavka.workshop15.infrastructure.configuration.DatabaseConfiguration.OPINION_TABLE;

@Slf4j
@Repository
@AllArgsConstructor
public class OpinionDatabaseRepository implements OpinionRepository {

    private static final String DELETE_ALL = "DELETE FROM OPINION WHERE 1=1";
    private static final String DELETE_ALL_WHERE_CUSTOMER_EMAIL =
            "DELETE FROM OPINION WHERE CUSTOMER_ID IN (SELECT ID FROM CUSTOMER WHERE EMAIL = :email)";
    private static final String SELECT_ALL_WHERE_CUSTOMER_EMAIL = """
            SELECT * FROM OPINION AS OPN
                INNER JOIN CUSTOMER AS CUS ON CUS.ID = OPN.CUSTOMER_ID
                WHERE CUS.EMAIL = :email
                ORDER BY DATE_TIME
            """;
    private static final String SELECT_ALL = "SELECT * FROM OPINION";
    private static final String SELECT_UNWANTED_OPINIONS = "SELECT * FROM OPINION WHERE STARS < 4";
    private static final String DELETE_UNWANTED_OPINIONS = "DELETE FROM OPINION WHERE STARS < 4";


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

    @Override
    public List<Opinion> findAll() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL, databaseMapper::mapOpinion);
    }


    @Override
    public List<Opinion> findUnwantedOpinions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_UNWANTED_OPINIONS, databaseMapper::mapOpinion);
    }

    @Override
    public List<Opinion> findAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        return jdbcTemplate.query(SELECT_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email), databaseMapper::mapOpinion);
    }

    @Override
    public void removeAll() {
        new JdbcTemplate(simpleDriverDataSource).execute(DELETE_ALL);
    }

    @Override
    public void removeUnwantedOpinions() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_UNWANTED_OPINIONS);
    }

    @Override
    public void removeAll(String email) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(simpleDriverDataSource);
        jdbcTemplate.update(DELETE_ALL_WHERE_CUSTOMER_EMAIL, Map.of("email", email));
    }
}
