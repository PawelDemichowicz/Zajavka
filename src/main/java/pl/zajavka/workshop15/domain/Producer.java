package pl.zajavka.workshop15.domain;

import lombok.*;

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Producer {
    private Long id;
    private String producerName;
    private String address;
}