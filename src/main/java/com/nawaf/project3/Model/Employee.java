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
public class Employee {

    @Id
    private Integer id;

    @NotEmpty(message = "Position required")
    @Size(min = 2, max = 50, message = "Position must be between 2 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z\\s]+$",
            message = "Position must contain letters only"
    )
    @Column(nullable = false, length = 50)
    private String position;

    @NotNull(message = "Salary required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salary must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "Salary must have at most 10 integer digits and 2 decimal places")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    @OneToOne
    @MapsId
    @JoinColumn
    @JsonIgnore
    private User user;
}