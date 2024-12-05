package pl.zajavka.workshop15.business;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.workshop15.domain.Customer;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private OpinionRepository opinionRepository;
    private PurchaseRepository purchaseRepository;

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.create(customer);
    }

    @Transactional
    public Customer find(String email) {
        return customerRepository.find(email)
                .orElseThrow(() -> new RuntimeException("Customer with email: [%s] is missing".formatted(email)));
    }

    @Transactional
    public void removeAll() {
        purchaseRepository.removeAll();
        opinionRepository.removeAll();
        customerRepository.removeAll();
    }

    @Transactional
    public void remove(String email) {
        Customer existingCustomer = find(email);

        if (isOlderThan40(existingCustomer)) {
            throw new RuntimeException("Could not remove customer because he/she is older than 40");
        }

        purchaseRepository.removeAll(email);
        opinionRepository.removeAll(email);
        customerRepository.remove(email);
    }

    private boolean isOlderThan40(Customer customer) {
        return LocalDate.now().getYear() - customer.getDateOfBirth().getYear() > 40;
    }
}


