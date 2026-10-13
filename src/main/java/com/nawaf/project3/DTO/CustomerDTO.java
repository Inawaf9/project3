package com.nawaf.project3.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @NotEmpty(message = "Username required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers and underscores")
    private String username;

    @NotEmpty(message = "Password required")
    @Size(min = 8, max = 75, message = "Password must be between 8 and 75 characters")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,72}$",
            message = "Password must contain uppercase, lowercase, number and special character"
    )
    private String password;

    @NotEmpty(message = "Name required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotEmpty(message = "Email required")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty(message = "Phone number required")
    @Pattern(
            regexp = "^05\\d{8}$",
            message = "Phone number must start with 05 and contain 10 digits"
    )
    private String phoneNumber;
}