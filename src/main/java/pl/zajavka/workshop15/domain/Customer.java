package pl.zajavka.workshop15.domain;

import lombok.*;

import java.time.LocalDate;

@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private Long id;
    private String userName;
    private String email;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String telephoneNumber;
}