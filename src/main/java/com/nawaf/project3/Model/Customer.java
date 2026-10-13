package com.nawaf.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private Integer id;

    @NotEmpty(message = "Phone number required")
    @Pattern(
            regexp = "^05\\d{8}$",
            message = "Phone number must start with 05 and contain exactly 10 digits"
    )
    @Column(nullable = false, unique = true, length = 10)
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "customer")
    private Set<Account> accounts;
}