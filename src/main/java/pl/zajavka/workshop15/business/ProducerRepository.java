package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Producer;

import java.util.List;

public interface ProducerRepository {
    Producer create(Producer producer);

    List<Producer> findAll();

    void removeAll();
}
