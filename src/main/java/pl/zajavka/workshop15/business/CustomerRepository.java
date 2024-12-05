package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer create(Customer customer);

    Optional<Customer> find(String email);

    void removeAll();

    void remove(String email);
}
