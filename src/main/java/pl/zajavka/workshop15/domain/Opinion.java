package pl.zajavka.workshop15.domain;

import lombok.*;

import java.time.OffsetDateTime;

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Opinion {
    private Long id;
    private Customer customer;
    private Product product;
    private Byte stars;
    private String comment;
    private OffsetDateTime dateTime;
}
