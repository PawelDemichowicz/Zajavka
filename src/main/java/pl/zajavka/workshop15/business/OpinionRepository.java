package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Opinion;

public interface OpinionRepository {
    Opinion create(Opinion opinion);
}
