package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Customer create(Customer customer);

    Optional<Customer> find(String email);

    List<Customer> findAll();

    void removeAll();

    void remove(String email);
}
