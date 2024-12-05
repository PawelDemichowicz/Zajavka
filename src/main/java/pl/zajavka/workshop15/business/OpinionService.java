package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Customer;
import pl.zajavka.workshop15.domain.Opinion;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionService {

    private OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        return opinionRepository.create(opinion);
    }

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();
    }

    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }
}
