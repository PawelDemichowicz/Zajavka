package pl.zajavka.domain;

import pl.zajavka.workshop15.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class StoreFixtures {

    public static Customer someCustomer() {
        return Customer.builder()
                .userName("zajavkowyzajavkowicz")
                .email("zajavka@gmail.com")
                .name("zajavkowicz")
                .surname("zajavkowy")
                .dateOfBirth(LocalDate.of(1984, 10, 2))
                .telephoneNumber("+124456545642")
                .build();
    }

    public static Producer someProducer() {
        return Producer.builder()
                .producerName("someProducer")
                .address("Some address")
                .build();
    }

    public static Product someProduct1(Producer producer) {
        return Product.builder()
                .productCode("productCode1")
                .productName("productName1")
                .productPrice(BigDecimal.valueOf(150.19))
                .adultsOnly(false)
                .description("Some description")
                .producer(producer)
                .build();
    }

    public static Product someProduct2(Producer producer) {
        return Product.builder()
                .productCode("productCode2")
                .productName("productName2")
                .productPrice(BigDecimal.valueOf(323.19))
                .adultsOnly(false)
                .description("Some description")
                .producer(producer)
                .build();
    }

    public static Purchase somePurchase(Customer customer, Product product) {
        return Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(2)
                .dateTime(OffsetDateTime.of(2020, 1, 1, 10, 9, 10, 0, ZoneOffset.ofHours(4)))
                .build();
    }

    public static Opinion someOpinion(Customer customer, Product product) {
        return Opinion.builder()
                .customer(customer)
                .product(product)
                .stars((byte) 4)
                .comment("My comment")
                .dateTime(OffsetDateTime.of(2020, 1, 1, 12, 9, 10, 0, ZoneOffset.ofHours(4)))
                .build();
    }
}
