package pl.zajavka.workshop15.business;

import pl.zajavka.workshop15.domain.Customer;

public interface CustomerRepository {
    Customer create(Customer customer);
}
