package pl.zajavka.workshop15.domain;

import lombok.*;

import java.math.BigDecimal;

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    private Long id;
    private String productCode;
    private String productName;
    private BigDecimal productPrice;
    private Boolean adultsOnly;
    private String description;
    private Producer producer;
}
