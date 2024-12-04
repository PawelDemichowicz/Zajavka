package pl.zajavka.workshop15.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {
    private Long id;
    private Customer customer;
    private Product product;
    private Integer quantity;
    private OffsetDateTime dateTime;
}