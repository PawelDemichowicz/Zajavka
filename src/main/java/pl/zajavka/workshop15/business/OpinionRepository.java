package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Opinion;

import java.util.List;

public interface OpinionRepository {
    Opinion create(Opinion opinion);

    void removeAll();

    void removeAll(String email);

    List<Opinion> findAll();

    List<Opinion> findAll(String email);

    List<Opinion> findUnwantedOpinions();

    void removeUnwantedOpinions();
}
