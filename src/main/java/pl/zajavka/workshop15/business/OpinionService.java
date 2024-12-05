package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Opinion;
import pl.zajavka.workshop15.domain.Purchase;

import java.util.List;

@Service
@AllArgsConstructor
public class OpinionService {

    private static final Logger log = LoggerFactory.getLogger(OpinionService.class);
    private PurchaseService purchaseService;
    private OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        String email = opinion.getCustomer().getEmail();
        String productCode = opinion.getProduct().getProductCode();
        List<Purchase> purchases = purchaseService.findAll(email, productCode);
        log.debug("Customer: [{}] made: [{}] purchases for product: [{}]", email, purchases, productCode);

        if (purchases.isEmpty()) {
            throw new RuntimeException(
                    "Customer: [%s] wants to give opinion for product: [%s] but there is no purchase"
                            .formatted(email, productCode)
            );
        }
        return opinionRepository.create(opinion);
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    public List<Opinion> findUnwantedOpinions() {
        return opinionRepository.findUnwantedOpinions();
    }

    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();
    }

    @Transactional
    public void removeUnwantedOpinions() {
        opinionRepository.removeUnwantedOpinions();
    }

    public boolean customerGivesUnwantedOpinions(String email) {
        return opinionRepository.customerGivesUnwantedOpinions(email);
    }
}
