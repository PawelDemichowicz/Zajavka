package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);

    List<Opinion> findAll();

    List<Opinion> findAll(String email);

    List<Opinion> findUnwantedOpinions();

    List<Opinion> findAllByProductCode(String productCode);

    void removeAll();

    void removeAll(String email);

    void removeUnwantedOpinions();

    void removeAllByProductCode(String productCode);

    boolean customerGivesUnwantedOpinions(String email);
}
