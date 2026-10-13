package com.nawaf.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Account number required")
    @Pattern(
            regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$",
            message = "Account number must follow XXXX-XXXX-XXXX-XXXX format"
    )
    @Column(nullable = false, unique = true, length = 19)
    private String accountNumber;

    @NotNull(message = "Balance required")
    @DecimalMin(value = "0.0", message = "Balance cannot be negative")
    @Digits(integer = 17, fraction = 2, message = "Balance must have at most 17 integer digits and 2 decimal places")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull(message = "Account status required")
    @Column(nullable = false)
    private Boolean isActive = false;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Customer customer;
}