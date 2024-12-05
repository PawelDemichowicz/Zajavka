package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.domain.Producer;

@Service
@AllArgsConstructor
public class ProducerService {

    private ProductRepository productRepository;
    private ProducerRepository producerRepository;

    @Transactional
    public Producer create(Producer producer) {
        return producerRepository.create(producer);
    }

    @Transactional
    public void removeAll() {
        productRepository.removeAll();
        producerRepository.removeAll();
    }
}