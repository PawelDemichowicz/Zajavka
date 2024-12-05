package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Purchase;

import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService {

    private PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase create(Purchase purchase) {
        return purchaseRepository.create(purchase);
    }

    @Transactional
    public void removeAll() {
        purchaseRepository.removeAll();
    }

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> findAll(String email) {
        return purchaseRepository.findAll(email);
    }

    public List<Purchase> findAll(String email, String productCode) {
        return purchaseRepository.findAll(email, productCode);
    }
}