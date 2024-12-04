package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Producer;

public interface ProducerRepository {
    Producer create(Producer producer);
}
